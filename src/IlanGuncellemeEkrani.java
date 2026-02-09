import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IlanGuncellemeEkrani extends JFrame {
    private JTextField tKonum, tFiyat, tTur, tMetrekare, tAdaNo, tParselNo;
    private String eskiSatir, email, ilanTuru;
    private IlanlarimiYonetEkrani anaEkran; 

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);

    public IlanGuncellemeEkrani(String secilenSatir, String email, IlanlarimiYonetEkrani anaEkran) {
        this.eskiSatir = secilenSatir;
        this.email = email;
        this.anaEkran = anaEkran;

        String[] veri = secilenSatir.split("\\|"); 
        this.ilanTuru = veri[3];

        setTitle("İlan Güncelle");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(cBej);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Konum:"));
        tKonum = new JTextField(veri[1]);
        panel.add(tKonum);

        panel.add(new JLabel("Fiyat:"));
        tFiyat = new JTextField(veri[2]);
        panel.add(tFiyat);

        if (ilanTuru.equals("KONUT") || ilanTuru.equals("ISYERI")) {
            panel.add(new JLabel("Tür:"));
            tTur = new JTextField(veri[4]);
            panel.add(tTur);

            panel.add(new JLabel("Metrekare:"));
            tMetrekare = new JTextField(veri[5]);
            panel.add(tMetrekare);
        } else if (ilanTuru.equals("ARSA")) {
            panel.add(new JLabel("Ada No:"));
            tAdaNo = new JTextField(veri[4]);
            panel.add(tAdaNo);

            panel.add(new JLabel("Parsel No:"));
            tParselNo = new JTextField(veri[5]);
            panel.add(tParselNo);
        }

        add(panel, BorderLayout.CENTER);

        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBackground(cMavi);
        btnGuncelle.setForeground(Color.WHITE);
        btnGuncelle.setOpaque(true);
        btnGuncelle.setBorderPainted(false);
        add(btnGuncelle, BorderLayout.SOUTH);

        btnGuncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Ilan yeniIlan = null;
                    TXTVeriYonetimi vm = new TXTVeriYonetimi();

                    if (ilanTuru.equals("KONUT")) {
                        yeniIlan = new Konut(email, tKonum.getText(), Double.parseDouble(tFiyat.getText()),
                                tTur.getText(), Integer.parseInt(tMetrekare.getText()), veri[6]);
                    } else if (ilanTuru.equals("ARSA")) {
                        yeniIlan = new Arsa(email, tKonum.getText(), Double.parseDouble(tFiyat.getText()),
                                tAdaNo.getText(), tParselNo.getText(), veri[6]);
                    } else if (ilanTuru.equals("ISYERI")) {
                        yeniIlan = new IsYeri(email, tKonum.getText(), Double.parseDouble(tFiyat.getText()),
                                tTur.getText(), Integer.parseInt(tMetrekare.getText()), veri[6]);
                    }

                    vm.ilanGuncelle(eskiSatir, yeniIlan, email);
                    JOptionPane.showMessageDialog(IlanGuncellemeEkrani.this, "İlan güncellendi.");
                    
                    if (anaEkran != null) {
                        anaEkran.ilanlariGetir();
                    }
                    
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(IlanGuncellemeEkrani.this, "Beklenmeyen hata oluştu.");
                }
            }
        });
    }
}
