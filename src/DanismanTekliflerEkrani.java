import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DanismanTekliflerEkrani extends JFrame {
    private String email;
    private DefaultListModel<String> listeModeli;
    private JList<String> teklifListesi;
    private TXTVeriYonetimi veriMotoru;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public DanismanTekliflerEkrani(String email) {
        this.email = email;
        this.veriMotoru = new TXTVeriYonetimi();
        
        setTitle("Gelen Teklifler");
        setSize(900, 500); 
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
        
        JButton btnKabulEt = new JButton("Kabul Et");
        JButton btnReddet = new JButton("Reddet");
        JButton btnKarsiTeklif = new JButton("Karşı Teklif");
        
        JButton[] butonlar = {btnKabulEt, btnReddet, btnKarsiTeklif};
        for (JButton btn : butonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            butonPaneli.add(btn);
        }
        
        add(butonPaneli, BorderLayout.SOUTH);

        btnKabulEt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = teklifListesi.getSelectedValue();
                
                if (secilenSatir != null) {

                    String[] v = secilenSatir.split("\\|");
                    if (!v[5].equals("BEKLEMEDE")) {
                        JOptionPane.showMessageDialog(null, "Bu teklif zaten sonuçlandırılmış!");
                        return;
                    }
                    veriMotoru.teklifDurumGuncelle(secilenSatir, "KABUL", 0);
                    JOptionPane.showMessageDialog(null, "Teklif kabul edildi!");
                    teklifleriGetir();
                }
            }
        });

        btnReddet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = teklifListesi.getSelectedValue();
                
                if (secilenSatir != null) {
                	
                	String[] v = secilenSatir.split("\\|");
                	if (!v[5].equals("BEKLEMEDE")) {
                	    JOptionPane.showMessageDialog(null, "Bu teklif zaten sonuçlandırılmış!");
                	    return;
                	}
                    veriMotoru.teklifDurumGuncelle(secilenSatir, "RED", 0);
                    JOptionPane.showMessageDialog(null, "Teklif reddedildi!");
                    teklifleriGetir();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir teklif seçin!");
                }
            }
        });

        btnKarsiTeklif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenSatir = teklifListesi.getSelectedValue();
                
                if (secilenSatir != null) {
                	
                	String[] v = secilenSatir.split("\\|");
                	if (!v[5].equals("BEKLEMEDE")) {
                	    JOptionPane.showMessageDialog(null, "Bu teklif zaten sonuçlandırılmış!");
                	    return;
                	}

                    String girilenFiyat = JOptionPane.showInputDialog(
                        null, 
                        "Karşı Teklif Fiyatınzı Giriniz:", 
                        "Karşı Teklif", 
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (girilenFiyat != null && !girilenFiyat.isEmpty()) {
                        try {
                            double yeniFiyat = Double.parseDouble(girilenFiyat);
                            veriMotoru.teklifDurumGuncelle(secilenSatir, "KARSI_TEKLIF", yeniFiyat);
                            JOptionPane.showMessageDialog(null, "Karşı teklif gönderildi!");
                            teklifleriGetir();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Hata: Geçerli bir sayı giriniz!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir teklif seçin!");
                }
            }
        });
    }

    private void teklifleriGetir() {
        listeModeli.clear();
        ArrayList<String> teklifler = veriMotoru.teklifleriGetir(email, "DANISMAN");
        
        for (String teklif : teklifler) {
            listeModeli.addElement(teklif);
        }
    }
}