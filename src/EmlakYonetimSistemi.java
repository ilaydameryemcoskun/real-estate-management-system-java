import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmlakYonetimSistemi extends JFrame {
    private TXTVeriYonetimi veriYonetimi;
    private String email;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public EmlakYonetimSistemi(String email) {
        this.email = email;
        this.veriYonetimi = new TXTVeriYonetimi();

        setTitle("Emlak Yönetim Sistemi");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(cVizon);
        ustPanel.add(new JLabel("Hoş geldiniz! İlan işlemlerinizi buradan yönetebilirsiniz."));
        add(ustPanel, BorderLayout.NORTH);

        JPanel ortaPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        ortaPanel.setBackground(cBej);
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnIlanEkle = new JButton("Yeni İlan Ekle");
        JButton btnIlanlarimiYonet = new JButton("İlanlarımı Yönet");
        JButton btnDanismanIstatistik = new JButton("Danışman İstatistikleri");
        JButton btnTeklifler = new JButton("Gelen Teklifler");

        JButton[] butonlar = {btnIlanEkle, btnIlanlarimiYonet, btnDanismanIstatistik, btnTeklifler};
        for (JButton btn : butonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            ortaPanel.add(btn);
        }

        add(ortaPanel, BorderLayout.CENTER);

        JButton btnCikis = new JButton("Oturumu Kapat");
        btnCikis.setBackground(cGri);
        btnCikis.setForeground(Color.WHITE);
        btnCikis.setOpaque(true);
        btnCikis.setBorderPainted(false);
        add(btnCikis, BorderLayout.SOUTH);

        btnCikis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmlakYonetimSistemi.this.dispose();
                new GirisEkrani().setVisible(true);
            }
        });

        btnIlanEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IlanEklemeEkrani(email).setVisible(true);
            }
        });

        btnIlanlarimiYonet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IlanlarimiYonetEkrani(email).setVisible(true);
            }
        });

        btnDanismanIstatistik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DanismanIstatistikEkrani(email).setVisible(true);
            }
        });
        
        btnTeklifler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DanismanTekliflerEkrani(email).setVisible(true);
            }
        });
    }
}