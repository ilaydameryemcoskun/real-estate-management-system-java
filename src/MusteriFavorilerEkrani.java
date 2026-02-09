import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MusteriFavorilerEkrani extends JFrame {
    private String email;
    private DefaultListModel<String> listeModeli;
    private JList<String> favoriListesi;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);

    public MusteriFavorilerEkrani(String email) {
        this.email = email;
        setTitle("Favori İlanlarım");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listeModeli = new DefaultListModel<>();
        favoriListesi = new JList<>(listeModeli);
        favoriListesi.setBackground(cBej);
        favoriListesi.setSelectionBackground(cVizon);
        favoriListesi.setSelectionForeground(Color.BLACK);
        
        add(new JScrollPane(favoriListesi), BorderLayout.CENTER);

        JPanel butonPaneli = new JPanel();
        butonPaneli.setBackground(cVizon);
        
        JButton btnDetayGor = new JButton("Detay Gör");
        JButton btnFavoriSil = new JButton("Favorilerden Sil");

        JButton[] butonlar = {btnDetayGor, btnFavoriSil};
        for (JButton btn : butonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            butonPaneli.add(btn);
        }
        
        add(butonPaneli, BorderLayout.SOUTH);

        favorileriGetir();

        btnDetayGor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = favoriListesi.getSelectedValue();
                if (secilenSatir == null) {
                    JOptionPane.showMessageDialog(null, "Lütfen bir ilan seçin!");
                    return;
                }
                String[] parcalar = secilenSatir.split("\\|", 2);
                String sadeceIlan = parcalar[1];
                new MusteriIlanDetayEkrani(sadeceIlan, email).setVisible(true);
            }
        });

        btnFavoriSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = favoriListesi.getSelectedValue();
                if (secilenSatir == null) {
                    JOptionPane.showMessageDialog(null, "Lütfen silmek istediğiniz ilanı seçin!");
                    return;
                }
                listeModeli.removeElement(secilenSatir);
                favorileriDosyayaYaz();
                JOptionPane.showMessageDialog(null, "İlan favorilerinizden silindi.");
            }
        });
    }

    private void favorileriGetir() {
        listeModeli.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("favoriler.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parcalar = satir.split("\\|");
                if (parcalar.length > 0) {
                    if (parcalar[0].equals(email)) {
                        listeModeli.addElement(satir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void favorileriDosyayaYaz() {
        ArrayList<String> digerKullaniciFavorileri = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("favoriler.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parcalar = satir.split("\\|");
                if (parcalar.length > 0) {
                    if (!parcalar[0].equals(email)) {
                        digerKullaniciFavorileri.add(satir);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        try (PrintWriter pw = new PrintWriter(new FileWriter("favoriler.txt"))) {
            for (String s : digerKullaniciFavorileri) {
                pw.println(s);
            }
            for (int i = 0; i < listeModeli.size(); i++) {
                pw.println(listeModeli.getElementAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}