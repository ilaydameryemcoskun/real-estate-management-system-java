public class IsYeri extends Ilan {

    private String isYeriTuru;
    private int metrekare;

    public IsYeri(String danismanEmail, String konum, double fiyat,
                  String isYeriTuru, int metrekare, String durum) {

        super(danismanEmail, konum, fiyat, durum);
        this.isYeriTuru = isYeriTuru;
        this.metrekare = metrekare;
    }
    public String getIsYeriTuru() {
        return isYeriTuru;
    }
    public int getMetrekare() {
        return metrekare;
    }
    @Override
    public String getIlanTuru() {
        return "ISYERI";
    }
}


