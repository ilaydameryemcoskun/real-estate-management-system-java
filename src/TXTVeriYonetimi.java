import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TXTVeriYonetimi implements VeriYonetimi {

	@Override
	public void kaydet(Kullanici k, String rol) { 
	    try (PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true))) {
	        String satir = k.getAdSoyad() + "|" + k.getTelefon() + "|" + k.getEmail() + "|" + k.getSifre() + "|" + rol;
	        pw.println(satir);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Kayıt hatası!");
	    }
	}

    @Override
    public void ilanKaydet(Ilan ilan, String danismanEmail) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("ilanlar.txt", true))) {
            String satir = danismanEmail + "|" + ilan.getKonum() + "|" + ilan.getFiyat() + "|" + ilan.getIlanTuru();
            if (ilan.getIlanTuru().equals("KONUT")) {
                Konut k = (Konut) ilan;
                satir = satir + "|" + k.getKonutTuru() + "|" + k.getMetrekare() + "|AKTIF";
            } else if (ilan.getIlanTuru().equals("ARSA")) {
                Arsa a = (Arsa) ilan;
                satir = satir + "|" + a.getAdaNo() + "|" + a.getParselNo() + "|AKTIF";
            } else if (ilan.getIlanTuru().equals("ISYERI")) {
                IsYeri i = (IsYeri) ilan;
                satir = satir + "|" + i.getIsYeriTuru() + "|" + i.getMetrekare() + "|AKTIF";
            }
            pw.println(satir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "İlan kayıt hatası!");
        }
    }

    @Override
    public String[] yukle(String email, String sifre) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split("\\|");
                if (d.length >= 5 && d[2].equals(email) && d[3].equals(sifre)) {
                    return new String[]{email, d[4]};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ilanSil(String silinecekSatir) {
        ArrayList<String> satirlar = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().equals(silinecekSatir.trim())) {
                    satirlar.add(satir);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        try (PrintWriter pw = new PrintWriter(new FileWriter("ilanlar.txt"))) {
            for (String s : satirlar) { pw.println(s); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void ilanGuncelle(String eskiSatir, Ilan yeniIlan, String email) {
        ilanSil(eskiSatir);
        ilanKaydet(yeniIlan, email);
    }

    @Override
    public void ilanDurumGuncelle(String ilanSatiri, String yeniDurum) {
        ArrayList<String> satirlar = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().equals(ilanSatiri.trim())) {
                    String[] v = satir.split("\\|");
                    satir = v[0] + "|" + v[1] + "|" + v[2] + "|" + v[3] + "|" + v[4] + "|" + v[5] + "|" + yeniDurum;
                }
                satirlar.add(satir);
            }
        } catch (Exception e) { e.printStackTrace(); }

        try (PrintWriter pw = new PrintWriter(new FileWriter("ilanlar.txt"))) {
            for (String s : satirlar) { pw.println(s); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public String[] IstatistikGetir(String email) {
        int toplam = 0, aktif = 0, satilan = 0;
        int sKonut = 0, sArsa = 0, sIsyeri = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] v = satir.split("\\|");
                if (v[0].equals(email)) {
                    toplam++;
                    if (v[6].equals("AKTIF")) { aktif++; }
                    else if (v[6].equals("SATILDI")) {
                        satilan++;
                        if (v[3].equals("KONUT")) sKonut++;
                        else if (v[3].equals("ARSA")) sArsa++;
                        else if (v[3].equals("ISYERI")) sIsyeri++;
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return new String[]{
            String.valueOf(toplam), String.valueOf(aktif), String.valueOf(satilan),
            String.valueOf(sKonut), String.valueOf(sArsa), String.valueOf(sIsyeri), "0"
        };
    }

    @Override
    public void teklifKaydet(String musteriEmail, String danismanEmail,
                             String ilanKonum, double ilanFiyat, double teklifFiyat) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("teklifler.txt", true))) {
            pw.println(musteriEmail + "|" + danismanEmail + "|" + ilanKonum + "|" + ilanFiyat + "|" + teklifFiyat + "|BEKLEMEDE|0");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void teklifDurumGuncelle(String teklifSatiri, String durum, double karsiTeklifFiyat) {
        ArrayList<String> satirlar = new ArrayList<>();
        String hedefIlanBilgisi = ""; 

        try (BufferedReader br = new BufferedReader(new FileReader("teklifler.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().equals(teklifSatiri.trim())) {
                    String[] v = satir.split("\\|");
                   
                    hedefIlanBilgisi = v[2]; 
                    satir = v[0] + "|" + v[1] + "|" + v[2] + "|" + v[3] + "|" + v[4] + "|" + durum + "|" + karsiTeklifFiyat;
                }
                satirlar.add(satir);
            }
        } catch (Exception e) { e.printStackTrace(); }

       
        try (PrintWriter pw = new PrintWriter(new FileWriter("teklifler.txt"))) {
            for (String s : satirlar) { pw.println(s); }
        } catch (Exception e) { e.printStackTrace(); }

        
        if (durum.equals("KABUL") && !hedefIlanBilgisi.isEmpty()) {
            otomatikIlanSat(hedefIlanBilgisi);
        }
    }
    
    private void otomatikIlanSat(String ilanBilgisi) {
        ArrayList<String> ilanlar = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] v = satir.split("\\|");
                
                String mevcutIlanBilgi = v[1] + " (" + v[3] + ")";
                
                if (mevcutIlanBilgi.equals(ilanBilgisi) && v[6].equals("AKTIF")) {
                   
                    satir = v[0] + "|" + v[1] + "|" + v[2] + "|" + v[3] + "|" + v[4] + "|" + v[5] + "|SATILDI";
                }
                ilanlar.add(satir);
            }
        } catch (Exception e) { e.printStackTrace(); }

        try (PrintWriter pw = new PrintWriter(new FileWriter("ilanlar.txt"))) {
            for (String s : ilanlar) { pw.println(s); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public ArrayList<String> teklifleriGetir(String email, String rol) {
        ArrayList<String> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("teklifler.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] v = satir.split("\\|");
                if (rol.equals("DANISMAN") && v[1].equals(email)) { liste.add(satir); }
                else if (rol.equals("MUSTERI") && v[0].equals(email)) { liste.add(satir); }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return liste;
    }
}

