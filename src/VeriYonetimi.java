import java.util.ArrayList;

public interface VeriYonetimi {
	void kaydet(Kullanici k, String rol);
    void ilanKaydet(Ilan ilan, String danismanEmail);
    String[] yukle(String email, String sifre);
    void ilanSil(String silinecekSatir);
    void ilanGuncelle(String eskiSatir, Ilan yeniIlan, String email);
    void ilanDurumGuncelle(String ilanSatiri, String yeniDurum);
    String[] IstatistikGetir(String email);
    void teklifKaydet(String musteriEmail, String danismanEmail, String ilanKonum, double ilanFiyat, double teklifFiyat);
    void teklifDurumGuncelle(String teklifSatiri, String durum, double karsiTeklifFiyat);
    ArrayList<String> teklifleriGetir(String email, String rol);
}
