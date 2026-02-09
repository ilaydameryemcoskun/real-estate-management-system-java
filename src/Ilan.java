public abstract class Ilan {

    public String danismanEmail;
    public String konum;
    public double fiyat;
    public String durum;

    public Ilan(String danismanEmail, String konum, double fiyat, String durum) {
        this.danismanEmail = danismanEmail;
        this.konum = konum;
        this.fiyat = fiyat;
        this.durum = durum;
    }
    public String getDanismanEmail() {
        return danismanEmail;
    }
    public String getKonum() {
        return konum;
    }
    public double getFiyat() {
        return fiyat;
    }
    public String getDurum() {
        return durum;
    }
    public void setDurum(String durum) {
        this.durum = durum;
    }
    public abstract String getIlanTuru();
}


