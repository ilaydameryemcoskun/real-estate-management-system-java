import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KayitEkrani extends JFrame {

    private JTextField txtAdSoyad, txtTelefon, txtEmail;
    private JPasswordField txtSifre;
    private JRadioButton rbDanisman, rbMusteri;
    private JButton btnKaydet, btnIptal;

    private VeriYonetimi veri;
    private GirisEkrani anaEkran; 
    
    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public KayitEkrani(GirisEkrani gelenEkran) { 
        this.anaEkran = gelenEkran; 
        
        veri = new TXTVeriYonetimi();

        setTitle("Kayıt Ol");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        getContentPane().setBackground(cBej);

        add(new JLabel("  Ad Soyad:"));
        txtAdSoyad = new JTextField();
        add(txtAdSoyad);

        add(new JLabel("  Telefon:"));
        txtTelefon = new JTextField();
        add(txtTelefon);

        add(new JLabel("  Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("  Şifre:"));
        txtSifre = new JPasswordField();
        add(txtSifre);

        add(new JLabel("  Rol:"));
        JPanel rolPanel = new JPanel();
        rolPanel.setBackground(cBej);
        rbDanisman = new JRadioButton("Danışman");
        rbMusteri = new JRadioButton("Müşteri");
        
        rbDanisman.setBackground(cBej);
        rbMusteri.setBackground(cBej);
        
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbDanisman); grup.add(rbMusteri);
        rolPanel.add(rbDanisman); rolPanel.add(rbMusteri);
        add(rolPanel);

        btnKaydet = new JButton("Kaydet");
        btnIptal = new JButton("İptal");
        
        btnKaydet.setBackground(cMavi);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setOpaque(true);
        btnKaydet.setBorderPainted(false);

        btnIptal.setBackground(cGri);
        btnIptal.setForeground(Color.WHITE);
        btnIptal.setOpaque(true);
        btnIptal.setBorderPainted(false);

        add(btnKaydet);
        add(btnIptal);

        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kaydet(); 
            }
        });

        btnIptal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                if (anaEkran != null) {
                    anaEkran.setVisible(true);
                }
            }
        });
    }

    private void kaydet() {
        String adSoyad = txtAdSoyad.getText();
        String telefon = txtTelefon.getText();
        String email = txtEmail.getText();
        String sifre = new String(txtSifre.getPassword());

        if (adSoyad.isEmpty() || telefon.isEmpty() || email.isEmpty() || sifre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tüm alanları doldurunuz!");
            return;
        }

        String rol = rbDanisman.isSelected() ? "DANISMAN" : (rbMusteri.isSelected() ? "MUSTERI" : "");
        if (rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen rol seçiniz!");
            return;
        }

        Kullanici yeniKullanici;

        if (rol.equals("DANISMAN")) {
            yeniKullanici = new Danisman(adSoyad, telefon, email, sifre);
        } else {
            yeniKullanici = new Musteri(adSoyad, telefon, email, sifre);
        }

        veri.kaydet(yeniKullanici, rol);

        JOptionPane.showMessageDialog(this, "Kayıt başarılı! Şimdi giriş yapabilirsiniz.");
        this.dispose(); 
        
        if (anaEkran != null) {
            anaEkran.setVisible(true); 
        }
    }
}