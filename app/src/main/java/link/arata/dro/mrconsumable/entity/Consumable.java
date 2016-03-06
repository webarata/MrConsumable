package link.arata.dro.mrconsumable.entity;

import java.util.HashMap;
import java.util.Map;

public class Consumable {
    /** テーブル名 */
    public static final String NAME = "consumable";
    /** 消耗品ID */
    public static final String CONSUMABLE_ID = "consumable_id";
    /** 消耗品名 */
    public static final String CONSUMABLE_NAME = "consumable_name";
    /** 消耗品名フリガナ */
    public static final String CONSUMABLE_FURIGANA = "consumable_furigana";
    /** 消耗品備考 */
    public static final String CONSUMABLE_NOTE = "consumable_note";
    /** 消耗品最安値 */
    public static final String CONSUMABLE_PRICE = "consumable_price";
    /** 消耗品購入日 */
    public static final String CONSUMABLE_DATE = "consumable_date";
    /** 消耗品購入数 */
    public static final String CONSUMABLE_COUNT = "consumable_count";

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("consumableName", consumableName);
        map.put("consumablePrice", consumablePrice == -1 ? "-" : String.valueOf(consumablePrice));
        map.put("consumableDate", consumableDate.length() == 0 ? "----/--/--" : consumableDate);
        map.put("consumableCount", consumableCount == -1 ? "" : String.valueOf(consumableCount));

        return map;
    }

    private long consumableId;
    private String consumableName;
    private String consumableFurigana;
    private String consumableNote;
    private int consumablePrice;
    private String consumableDate;
    private int consumableCount;

    /**
     * デフォルトコンストラクタ
     */
    public Consumable() {
    }

    public long getConsumableId() {
        return consumableId;
    }

    public void setConsumableId(long consumableId) {
        this.consumableId = consumableId;
    }

    public String getConsumableName() {
        return consumableName;
    }

    public void setConsumableName(String consumableName) {
        this.consumableName = consumableName;
    }

    public String getConsumableFurigana() {
        return consumableFurigana;
    }

    public void setConsumableFurigana(String consumableFurigana) {
        this.consumableFurigana = consumableFurigana;
    }

    public String getConsumableNote() {
        return consumableNote;
    }

    public void setConsumableNote(String consumableNote) {
        this.consumableNote = consumableNote;
    }

    public int getConsumablePrice() {
        return consumablePrice;
    }

    public void setConsumablePrice(int consumablePrice) {
        this.consumablePrice = consumablePrice;
    }

    public String getConsumableDate() {
        return consumableDate;
    }

    public void setConsumableDate(String consumableDate) {
        this.consumableDate = consumableDate;
    }

    public int getConsumableCount() {
        return consumableCount;
    }

    public void setConsumableCount(int consumableCount) {
        this.consumableCount = consumableCount;
    }
}
