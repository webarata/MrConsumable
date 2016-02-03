package link.arata.android.mrconsumable.entity;

public class Shop {
    /** テーブル名 */
    public static final String NAME = "shop";
    /** 店ID */
    public static final String SHOP_ID = "shop_id";
    /** 店名 */
    public static final String SHOP_NAME = "shop_name";
    /** 店名フリガナ */
    public static final String SHOP_FURIGANA = "shop_furigana";

    private long shopId;
    private String shopName;
    private String shopFurigana;

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopFurigana() {
        return shopFurigana;
    }

    public void setShopFurigana(String shopFurigana) {
        this.shopFurigana = shopFurigana;
    }
}
