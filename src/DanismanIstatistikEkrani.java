import javax.swing.*;
import java.awt.*;

public class DanismanIstatistikEkrani extends JFrame {
	
	private String email;
	
	Color cBej = new Color(237, 225, 207);
	Color cVizon = new Color(217, 197, 188);
	Color cMavi = new Color(118, 161, 167);

	public DanismanIstatistikEkrani(String email) {
		this.email = email;
		
		setTitle("Danışman İstatistikleri");
		setSize(450, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		getContentPane().setBackground(cBej);
		
		setLayout(new GridLayout(7, 1, 5, 5));
		
		TXTVeriYonetimi vm = new TXTVeriYonetimi();
		String[] istatistikler = vm.IstatistikGetir(email);
		
		JLabel lblToplam = new JLabel("  Toplam İlan: " + istatistikler[0]);
		lblToplam.setForeground(new Color(50, 50, 50));
		add(lblToplam);

		JLabel lblAktif = new JLabel("  Aktif İlan: " + istatistikler[1]);
		lblAktif.setForeground(new Color(34, 139, 34));
		add(lblAktif);

		JLabel lblSatilan = new JLabel("  Satılan İlan: " + istatistikler[2]);
		lblSatilan.setForeground(new Color(178, 34, 34));
		add(lblSatilan);

		JLabel lblCizgi = new JLabel("  -----------------------------------");
		lblCizgi.setForeground(new Color(182, 173, 175));
		add(lblCizgi);

		JLabel lblSKonut = new JLabel("  Satılan Konut: " + istatistikler[3]);
		add(lblSKonut);

		JLabel lblSArsa = new JLabel("  Satılan Arsa: " + istatistikler[4]);
		add(lblSArsa);

		JLabel lblSIsyeri = new JLabel("  Satılan İşyeri: " + istatistikler[5]);
		add(lblSIsyeri);
	}
}