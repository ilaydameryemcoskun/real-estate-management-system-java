import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MusteriIlanListesiEkrani extends JFrame {
    private String email;
    private DefaultListModel<String> listeModeli;
    private JList<String> ilanListesi;

    private JTextField tMin, tMax, tKonumAra;
    private JRadioButton rbHepsi, rbKonut, rbArsa, rbIsyeri;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public MusteriIlanListesiEkrani(String email) {
        this.email = email;

        setTitle("Ilan Arama Sistemi");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel filtrePaneli = new JPanel(new FlowLayout()); 
        filtrePaneli.setBackground(cVizon);

        rbHepsi = new JRadioButton("Hepsi", true);
        rbKonut = new JRadioButton("Konut");
        rbArsa = new JRadioButton("Arsa");
        rbIsyeri = new JRadioButton("Is Yeri");
      
        JRadioButton[] radyolar = {rbHepsi, rbKonut, rbArsa, rbIsyeri};
        for (JRadioButton rb : radyolar) {
            rb.setBackground(cVizon);
        }

        ButtonGroup grup = new ButtonGroup();
        grup.add(rbHepsi); grup.add(rbKonut); grup.add(rbArsa); grup.add(rbIsyeri);

        tMin = new JTextField(5);
        tMax = new JTextField(5);
        tKonumAra = new JTextField(10);
        JButton btnAra = new JButton("Ara");
        
        btnAra.setBackground(cMavi);
        btnAra.setForeground(Color.WHITE);
        btnAra.setOpaque(true);
        btnAra.setBorderPainted(false);

        filtrePaneli.add(new JLabel("Tür:"));
        filtrePaneli.add(rbHepsi);
        filtrePaneli.add(rbKonut);
        filtrePaneli.add(rbArsa);
        filtrePaneli.add(rbIsyeri); 
        filtrePaneli.add(new JLabel(" | Min:"));
        filtrePaneli.add(tMin);
        filtrePaneli.add(new JLabel(" Max:"));
        filtrePaneli.add(tMax);
        filtrePaneli.add(new JLabel(" | Konum:"));
        filtrePaneli.add(tKonumAra);
        filtrePaneli.add(btnAra);

        add(filtrePaneli, BorderLayout.NORTH);

        listeModeli = new DefaultListModel<>();
        ilanListesi = new JList<>(listeModeli);
        ilanListesi.setBackground(cBej);
        ilanListesi.setSelectionBackground(cVizon);
        ilanListesi.setSelectionForeground(Color.BLACK);
        
        add(new JScrollPane(ilanListesi), BorderLayout.CENTER);

        JPanel butonPaneli = new JPanel(new FlowLayout());
        butonPaneli.setBackground(cVizon);
        
        JButton btnDetayGor = new JButton("İlan Detayını Gör");
        JButton btnFavoriEkle = new JButton("Favorilere Ekle");
        
        JButton[] altButonlar = {btnDetayGor, btnFavoriEkle};
        for (JButton btn : altButonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            butonPaneli.add(btn);
        }
        add(butonPaneli, BorderLayout.SOUTH);

        ilanlariGetir("Hepsi", 0, Double.MAX_VALUE, "");

        btnAra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String secilenTur = "Hepsi";
                if (rbKonut.isSelected()) secilenTur = "KONUT";
                else if (rbArsa.isSelected()) secilenTur = "ARSA";
                else if (rbIsyeri.isSelected()) secilenTur = "ISYERI";

                try {
                    double min = tMin.getText().isEmpty() ? 0 : Double.parseDouble(tMin.getText());
                    double max = tMax.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(tMax.getText());
                    String konum = tKonumAra.getText().trim();
                    ilanlariGetir(secilenTur, min, max, konum);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen sayıları kontrol edin!");
                }
            }
        });

        btnDetayGor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = ilanListesi.getSelectedValue();
                if (secilenSatir != null) {
                    new MusteriIlanDetayEkrani(secilenSatir, email).setVisible(true);
                }
            }
        });

        btnFavoriEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = ilanListesi.getSelectedValue();
                if (secilenSatir != null) {
                    favoriyeKaydet(secilenSatir);
                }
            }
        });
    }

    private void favoriyeKaydet(String ilanSatiri) {
        boolean zatenEklendi = false;
        try (BufferedReader br = new BufferedReader(new FileReader("favoriler.txt"))) {
            String satir;
            String kontrolSatiri = email + "|" + ilanSatiri;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().equals(kontrolSatiri.trim())) {
                    zatenEklendi = true;
                    break;
                }
            }
        } catch (IOException e) {}

        if (zatenEklendi) {
            JOptionPane.showMessageDialog(null, "Bu ilan zaten favorilerinizde ekli!");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("favoriler.txt", true))) {
            pw.println(email + "|" + ilanSatiri);
            JOptionPane.showMessageDialog(null, "Favorilere eklendi!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ilanlariGetir(String tur, double min, double max, String konumAra) {
        listeModeli.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split("\\|");
                if (d.length >= 7) {
                    double fiyat = Double.parseDouble(d[2]);
                    String ilanTuru = d[3];
                    String konum = d[1].toLowerCase();
                    String durum = d[6];

                    if (durum.equals("AKTIF")) {
                        boolean turUygun = tur.equals("Hepsi") || ilanTuru.equals(tur);
                        boolean fiyatUygun = fiyat >= min && fiyat <= max;
                        boolean konumUygun = konumAra.isEmpty() || konum.contains(konumAra.toLowerCase());

                        if (turUygun && fiyatUygun && konumUygun) {
                            listeModeli.addElement(satir);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
