import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IlanEklemeEkrani extends JFrame {
    private String email;
    private TXTVeriYonetimi veriMotoru = new TXTVeriYonetimi();
    private JPanel konutPanel, arsaPanel, isyeriPanel;

    Color cMavi = new Color(118, 161, 167);
    Color cBej = new Color(237, 225, 207);
    Color cVizon = new Color(217, 197, 188);
    Color cGri = new Color(182, 173, 175);

    public IlanEklemeEkrani(String email) {
        this.email = email;
        setTitle("İlan Ekle");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel ustMenu = new JPanel();
        ustMenu.setBackground(cVizon);
        JButton btnKonut = new JButton("Konut");
        JButton btnArsa = new JButton("Arsa");
        JButton btnIsyeri = new JButton("İş Yeri");
        
        JButton[] ustButonlar = {btnKonut, btnArsa, btnIsyeri};
        for (JButton btn : ustButonlar) {
            btn.setBackground(cMavi);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            ustMenu.add(btn);
        }
        add(ustMenu, BorderLayout.NORTH);

        JPanel ortaAlan = new JPanel(null);
        ortaAlan.setBackground(cBej);
        
        konutPanel = konutPanelOlustur();
        arsaPanel = arsaPanelOlustur();
        isyeriPanel = isyeriPanelOlustur();

        konutPanel.setBounds(0, 0, 600, 350);
        arsaPanel.setBounds(0, 0, 600, 350);
        isyeriPanel.setBounds(0, 0, 600, 350);

        ortaAlan.add(konutPanel);
        ortaAlan.add(arsaPanel);
        ortaAlan.add(isyeriPanel);
        add(ortaAlan, BorderLayout.CENTER);

        panelGoster(konutPanel);
        
        btnKonut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGoster(konutPanel);
            }
        });

        btnArsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGoster(arsaPanel);
            }
        });

        btnIsyeri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGoster(isyeriPanel);
            }
        });
    }

    private void panelGoster(JPanel secilenPanel) {
        konutPanel.setVisible(false);
        arsaPanel.setVisible(false);
        isyeriPanel.setVisible(false);
        secilenPanel.setVisible(true);
    }

    private JPanel konutPanelOlustur() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(cBej);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField tKonum = new JTextField();
        JTextField tFiyat = new JTextField();
        JTextField tTur = new JTextField();
        JTextField tMetrekare = new JTextField();
        JButton btnKaydet = new JButton("Kaydet");
        
        btnKaydet.setBackground(cMavi);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setOpaque(true);
        btnKaydet.setBorderPainted(false);
        

        panel.add(new JLabel("Konum:"));
        panel.add(tKonum);
        panel.add(new JLabel("Fiyat:"));
        panel.add(tFiyat);
        panel.add(new JLabel("Konut Türü (Daire/Villa):"));
        panel.add(tTur);
        panel.add(new JLabel("Metrekare:"));
        panel.add(tMetrekare);
        panel.add(new JLabel(""));
        panel.add(btnKaydet);
        
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Konut yeniKonut = new Konut(email, tKonum.getText(), 
                            Double.parseDouble(tFiyat.getText()),
                            tTur.getText(), 
                            Integer.parseInt(tMetrekare.getText()), 
                            "AKTIF");
                    
                    veriMotoru.ilanKaydet(yeniKonut, email);
                    JOptionPane.showMessageDialog(null, "Konut ilanı başarıyla kaydedildi!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: Sayısal alanları kontrol edin.");
                }
            }
        });
        return panel;
    }

    private JPanel arsaPanelOlustur() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(cBej);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField tKonum = new JTextField();
        JTextField tFiyat = new JTextField();
        JTextField tAdaNo = new JTextField();
        JTextField tParselNo = new JTextField();
        JButton btnKaydet = new JButton("Kaydet");
        
        btnKaydet.setBackground(cMavi);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setOpaque(true);
        btnKaydet.setBorderPainted(false);

        panel.add(new JLabel("Konum:"));
        panel.add(tKonum);
        panel.add(new JLabel("Fiyat:"));
        panel.add(tFiyat);
        panel.add(new JLabel("Ada No:"));
        panel.add(tAdaNo);
        panel.add(new JLabel("Parsel No:"));
        panel.add(tParselNo);
        panel.add(new JLabel(""));
        panel.add(btnKaydet);

        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Arsa yeniArsa = new Arsa(email, tKonum.getText(), 
                            Double.parseDouble(tFiyat.getText()),
                            tAdaNo.getText(), 
                            tParselNo.getText(), 
                            "AKTIF");
                    
                    veriMotoru.ilanKaydet(yeniArsa, email);
                    JOptionPane.showMessageDialog(null, "Arsa ilanı başarıyla kaydedildi!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: Fiyat alanını kontrol edin.");
                }
            }
        });
        return panel;
    }

    private JPanel isyeriPanelOlustur() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(cBej);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField tKonum = new JTextField();
        JTextField tFiyat = new JTextField();
        JTextField tTur = new JTextField();
        JTextField tMetrekare = new JTextField();
        JButton btnKaydet = new JButton("Kaydet");
        
        btnKaydet.setBackground(cMavi);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setOpaque(true);
        btnKaydet.setBorderPainted(false);

        panel.add(new JLabel("Konum:"));
        panel.add(tKonum);
        panel.add(new JLabel("Fiyat:"));
        panel.add(tFiyat);
        panel.add(new JLabel("Is Yeri Türü (Dükkan/Ofis/Depo):"));
        panel.add(tTur);
        panel.add(new JLabel("Metrekare:"));
        panel.add(tMetrekare);
        panel.add(new JLabel(""));
        panel.add(btnKaydet);

        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IsYeri yeniIsyeri = new IsYeri(email, tKonum.getText(), 
                            Double.parseDouble(tFiyat.getText()),
                            tTur.getText(), 
                            Integer.parseInt(tMetrekare.getText()), 
                            "AKTIF");
                    
                    veriMotoru.ilanKaydet(yeniIsyeri, email);
                    JOptionPane.showMessageDialog(null, "İş yeri ilanı başarıyla kaydedildi!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: Sayısal alanları kontrol edin.");
                }
            }
        });
        return panel;
    }
}

