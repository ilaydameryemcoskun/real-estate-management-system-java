public abstract class Kullanici {
    public String adSoyad;
    public String telefon;
    public String email;
    public String sifre;

    public Kullanici(String adSoyad, String telefon, String email, String sifre) {
        this.adSoyad = adSoyad;
        this.telefon = telefon;
        this.email = email;
        this.sifre = sifre;
    }

    public String getEmail() { return email; }
    public String getSifre() { return sifre; }
    public String getAdSoyad() { return adSoyad; }
    public String getTelefon() { return telefon; }
}