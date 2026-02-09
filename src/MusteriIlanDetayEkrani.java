import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusteriIlanDetayEkrani extends JFrame {
    private String ilanSatiri;
    private String musteriEmail;
    private TXTVeriYonetimi veriMotoru;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public MusteriIlanDetayEkrani(String ilanSatiri, String musteriEmail) {
        this.ilanSatiri = ilanSatiri;
        this.musteriEmail = musteriEmail;
        this.veriMotoru = new TXTVeriYonetimi();

        setTitle("İlan Detayı");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(cBej);

        String[] veri = ilanSatiri.split("\\|");
        
        JPanel detayPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        detayPanel.setBackground(cBej);
        detayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detayPanel.add(new JLabel(" Danisman Email: " + veri[0]));
        detayPanel.add(new JLabel(" Konum: " + veri[1]));
        detayPanel.add(new JLabel(" Fiyat: " + veri[2] + " TL"));
        detayPanel.add(new JLabel(" Ilan Turu: " + veri[3]));
        
        if (veri[3].equals("KONUT")) {
            detayPanel.add(new JLabel(" Konut Turu: " + veri[4]));
            detayPanel.add(new JLabel(" Metrekare: " + veri[5]));
        } else if (veri[3].equals("ARSA")) {
            detayPanel.add(new JLabel(" Ada No: " + veri[4]));
            detayPanel.add(new JLabel(" Parsel No: " + veri[5]));
        } else if (veri[3].equals("ISYERI")) {
            detayPanel.add(new JLabel(" Is Yeri Turu: " + veri[4]));
            detayPanel.add(new JLabel(" Metrekare: " + veri[5]));
        }
        
        JLabel ayirici = new JLabel(" -------------------------");
        ayirici.setForeground(cGri);
        detayPanel.add(ayirici);
        
        JLabel lblTeklifBaslik = new JLabel(" TEKLİF VER");
        lblTeklifBaslik.setForeground(cMavi);
        detayPanel.add(lblTeklifBaslik);
        
        add(detayPanel, BorderLayout.CENTER);

        JPanel teklifPanel = new JPanel(new FlowLayout());
        teklifPanel.setBackground(cVizon);
        
        JLabel lblTeklif = new JLabel("Teklif Fiyatı:");
        JTextField tTeklifFiyat = new JTextField(10);
        JButton btnTeklifVer = new JButton("Teklif Gönder");
        
        btnTeklifVer.setBackground(cMavi);
        btnTeklifVer.setForeground(Color.WHITE);
        btnTeklifVer.setOpaque(true);
        btnTeklifVer.setBorderPainted(false);
        
        teklifPanel.add(lblTeklif);
        teklifPanel.add(tTeklifFiyat);
        teklifPanel.add(btnTeklifVer);
        
        add(teklifPanel, BorderLayout.SOUTH);

        btnTeklifVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double teklifFiyat = Double.parseDouble(tTeklifFiyat.getText());
                    double ilanFiyat = Double.parseDouble(veri[2]);
                    String danismanEmail = veri[0];
                    String ilanBilgi = veri[1] + " (" + veri[3] + ")";
                    
                    veriMotoru.teklifKaydet(musteriEmail, danismanEmail, ilanBilgi, ilanFiyat, teklifFiyat);
                    JOptionPane.showMessageDialog(null, "Teklif başarıyla gönderildi!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: Sayısal değeri kontrol edin!");
                }
            }
        });
    }
}