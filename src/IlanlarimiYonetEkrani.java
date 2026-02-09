import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class IlanlarimiYonetEkrani extends JFrame {
    private String email;
    private DefaultListModel<String> listeModeli;
    private JList<String> ilanListesi;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public IlanlarimiYonetEkrani(String email) {
        this.email = email;
        setTitle("İlanlarımı Yönet");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listeModeli = new DefaultListModel<>();
        ilanListesi = new JList<>(listeModeli);
        
        ilanListesi.setBackground(cBej);
        ilanListesi.setSelectionBackground(cVizon); 
        ilanListesi.setSelectionForeground(Color.BLACK);
        
        add(new JScrollPane(ilanListesi), BorderLayout.CENTER);

        ilanlariGetir(); 

        JPanel butonPaneli = new JPanel();
        butonPaneli.setBackground(cVizon);
        
        JButton btnSil = new JButton("Seçileni Sil");
        JButton btnGuncelle = new JButton("Seçileni Güncelle");
        JButton btnSatildi = new JButton("Satıldı İşaretle");
        
        JButton[] butonlar = {btnGuncelle, btnSil, btnSatildi};
        for (JButton btn : butonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            butonPaneli.add(btn);
        }
        
        add(butonPaneli, BorderLayout.SOUTH);

        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilen = ilanListesi.getSelectedValue();
                if (secilen != null) {
                    new TXTVeriYonetimi().ilanSil(secilen);
                    JOptionPane.showMessageDialog(IlanlarimiYonetEkrani.this, "İlan Silindi!");
                    ilanlariGetir();
                }
            }
        });

        btnGuncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilen = ilanListesi.getSelectedValue();
                if (secilen != null) {
                    String[] parcalar = secilen.split("\\|");
                    String durum = parcalar[6]; 

                    if (durum.equals("SATILDI")) {
                    	JOptionPane.showMessageDialog(IlanlarimiYonetEkrani.this, "Satılmış bir ilanı güncelleyemezsiniz!");
                    } else {
                    	new IlanGuncellemeEkrani(secilen, email, IlanlarimiYonetEkrani.this).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(IlanlarimiYonetEkrani.this, "Lütfen bir ilan seçin!");
                }
            }
        });
        
        btnSatildi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilen = ilanListesi.getSelectedValue();
                if (secilen != null) {
                    String[] veri = secilen.split("\\|"); 
                    if (veri.length > 6 && veri[6].equals("AKTIF")) {
                        new TXTVeriYonetimi().ilanDurumGuncelle(secilen, "SATILDI");
                        JOptionPane.showMessageDialog(null, "İlan SATILDI olarak işaretlendi!");
                        ilanlariGetir();
                    }
                }
            }
        });
    }

    public void ilanlariGetir() {
        listeModeli.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("ilanlar.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parcalar = satir.split("\\|"); 
                if (parcalar[0].equals(email)) {
                    listeModeli.addElement(satir);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}