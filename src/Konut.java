public class Konut extends Ilan {

    private String konutTuru;
    private int metrekare;

    public Konut(String danismanEmail, String konum, double fiyat,
                 String konutTuru, int metrekare, String durum) {

        super(danismanEmail, konum, fiyat, durum);
        this.konutTuru = konutTuru;
        this.metrekare = metrekare;
    }
    public String getKonutTuru() {
        return konutTuru;
    }
    public int getMetrekare() {
        return metrekare;
    }
    @Override
    public String getIlanTuru() {
        return "KONUT";
    }
}


