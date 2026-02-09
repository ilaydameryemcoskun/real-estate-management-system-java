import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusteriAnaEkrani extends JFrame {
    private String email;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public MusteriAnaEkrani(String email) {
        this.email = email;

        setTitle("Müşteri Paneli");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(cVizon);
        ustPanel.add(new JLabel("Hoş geldiniz! İlan arama ve teklif verme işlemlerinizi buradan yapabilirsiniz."));
        add(ustPanel, BorderLayout.NORTH);

        JPanel ortaPanel = new JPanel(new GridLayout(3, 1, 15, 15)); 
        ortaPanel.setBackground(cBej);
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnIlanlariGor = new JButton("İlanlari Görüntüle");
        JButton btnTekliflerim = new JButton("Tekliflerim");
        JButton btnFavoriler = new JButton("Favori İlanlar"); 

        JButton[] butonlar = {btnIlanlariGor, btnTekliflerim, btnFavoriler};
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
                MusteriAnaEkrani.this.dispose();
                new GirisEkrani().setVisible(true);
            }
        });

        btnIlanlariGor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusteriIlanListesiEkrani(email).setVisible(true);
            }
        });

        btnTekliflerim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusteriTekliflerEkrani(email).setVisible(true);
            }
        });

        btnFavoriler.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusteriFavorilerEkrani(email).setVisible(true);
            }
        });
    }
}

