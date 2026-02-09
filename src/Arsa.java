public class Arsa extends Ilan {

    private String adaNo;
    private String parselNo;

    public Arsa(String danismanEmail, String konum, double fiyat,
                String adaNo, String parselNo, String durum) {
        super(danismanEmail, konum, fiyat, durum);
        this.adaNo = adaNo;
        this.parselNo = parselNo;
    }
    public String getAdaNo() {
        return adaNo;
    }
    public String getParselNo() {
        return parselNo;
    }
    @Override
    public String getIlanTuru() {
        return "ARSA";
    }
}
