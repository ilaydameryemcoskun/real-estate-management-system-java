import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusteriTekliflerEkrani extends JFrame {
    private String email;
    private DefaultListModel<String> listeModeli;
    private JList<String> teklifListesi;
    private TXTVeriYonetimi veriMotoru;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);

    public MusteriTekliflerEkrani(String email) {
        this.email = email;
        this.veriMotoru = new TXTVeriYonetimi();

        setTitle("Tekliflerim ve Durumları");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listeModeli = new DefaultListModel<>();
        teklifListesi = new JList<>(listeModeli);
        teklifListesi.setBackground(cBej); 
        teklifListesi.setSelectionBackground(cVizon); 
        teklifListesi.setSelectionForeground(Color.BLACK);
        
        add(new JScrollPane(teklifListesi), BorderLayout.CENTER);

        teklifleriGetir();

        JPanel butonPaneli = new JPanel();
        butonPaneli.setBackground(cVizon); 
        
        JButton btnKarsiTeklifKabul = new JButton("Gelen Karşı Teklifi Kabul Et");
        JButton btnKarsiTeklifRed = new JButton("Gelen Karşı Teklifi Reddet");
        
        JButton[] butonlar = {btnKarsiTeklifKabul, btnKarsiTeklifRed};
        for (JButton btn : butonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            butonPaneli.add(btn);
        }
        
        add(butonPaneli, BorderLayout.SOUTH);

        btnKarsiTeklifKabul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = teklifListesi.getSelectedValue();
                if (secilenSatir != null) {
                    if (secilenSatir.contains("KARSI_TEKLIF")) {
                        veriMotoru.teklifDurumGuncelle(secilenSatir, "KABUL", 0);
                        JOptionPane.showMessageDialog(null, "Teklifi kabul ettiniz!");
                        teklifleriGetir();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sadece 'KARSI_TEKLIF' durumunu kabul edebilirsiniz.");
                    }
                }
            }
        });

        btnKarsiTeklifRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = teklifListesi.getSelectedValue();
                if (secilenSatir != null) {
                    if (secilenSatir.contains("KARSI_TEKLIF")) {
                        veriMotoru.teklifDurumGuncelle(secilenSatir, "RED", 0);
                        JOptionPane.showMessageDialog(null, "Karşı teklifi reddettiniz.");
                        teklifleriGetir();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sadece 'KARSI_TEKLIF' durumunu reddedebilirsiniz.");
                    }
                }
            }
        });
    }

    private void teklifleriGetir() {
        listeModeli.clear();
        ArrayList<String> teklifler = veriMotoru.teklifleriGetir(email, "MUSTERI");
        for (String t : teklifler) {
            listeModeli.addElement(t);
        }
    }
}
