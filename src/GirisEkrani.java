import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GirisEkrani extends JFrame {
    JTextField tMail = new JTextField(15);
    JPasswordField tSifre = new JPasswordField(15);
    
    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cGri = new Color(182, 173, 175);
    
    TXTVeriYonetimi veriMotoru = new TXTVeriYonetimi();

    public GirisEkrani() {
        setTitle("Emlak Yönetim Sistemi - Giriş");
        setLayout(new GridLayout(3, 2, 10, 10));
        
        getContentPane().setBackground(cBej);

        add(new JLabel("  Email:"));
        add(tMail);
        add(new JLabel("  Şifre:"));
        add(tSifre);

        JButton btnGiris = new JButton("Giriş Yap");
        JButton btnKayitOl = new JButton("Hesap Oluştur");
        
        btnGiris.setBackground(cMavi);
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setOpaque(true);
        btnGiris.setBorderPainted(false);
        
        btnKayitOl.setBackground(cGri);
        btnKayitOl.setForeground(Color.WHITE);
        btnKayitOl.setOpaque(true);
        btnKayitOl.setBorderPainted(false);

        add(btnGiris);
        add(btnKayitOl);

        btnGiris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = tMail.getText();
                String pass = new String(tSifre.getPassword());

                String[] sonuc = veriMotoru.yukle(mail, pass);
                if (sonuc != null) {
                    JOptionPane.showMessageDialog(GirisEkrani.this, "Giriş Basarılı!");
                    GirisEkrani.this.dispose(); 
                    
                    String email = sonuc[0];
                    String rol = sonuc[1];
                    
                    if (rol.equals("DANISMAN")) {
                        new EmlakYonetimSistemi(email).setVisible(true);
                    } else if (rol.equals("MUSTERI")) {
                        new MusteriAnaEkrani(email).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(GirisEkrani.this, "Kullanıcı Bulunamadı veya Şifre Yanlış!");
                }
            }
        });

        btnKayitOl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GirisEkrani.this.setVisible(false); 
                new KayitEkrani(GirisEkrani.this).setVisible(true);
            }
        });

        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GirisEkrani().setVisible(true);
        });
    }
}
