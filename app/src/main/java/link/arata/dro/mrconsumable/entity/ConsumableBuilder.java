package link.arata.dro.mrconsumable.entity;

/**
 * Consumableのビルダー
 *
 * @author arata
 */
public class ConsumableBuilder {
    private long consumableId;
    private String consumableName;
    private String consumableFurigana;
    private String consumableNote;
    private int consumablePrice;
    private String consumableDate;
    private int consumableCount;

    public ConsumableBuilder() {
        // プリミティブは初期値をセットする
        consumableId = -1;
        consumablePrice = -1;
    }

    /**
     * Consumableのインスタンスを生成する
     * @return consumable
     */
    public Consumable createInstance() {
        Consumable consumable = new Consumable();
        consumable.setConsumableId(consumableId);
        consumable.setConsumableName(consumableName);
        consumable.setConsumableFurigana(consumableFurigana);
        consumable.setConsumableNote(consumableNote);
        consumable.setConsumablePrice(consumablePrice);
        consumable.setConsumableDate(consumableDate);
        consumable.setConsumableCount(consumableCount);

        return consumable;
    }

    public ConsumableBuilder setConsumableId(long consumableId) {
        this.consumableId = consumableId;
        return this;
    }

    public ConsumableBuilder setConsumableName(String consumableName) {
        this.consumableName = consumableName;
        return this;
    }

    public ConsumableBuilder setConsumableFurigana(String consumableFurigana) {
        this.consumableFurigana = consumableFurigana;
        return this;
    }

    public ConsumableBuilder setConsumableNote(String consumableNote) {
        this.consumableNote = consumableNote;
        return this;
    }

    public ConsumableBuilder setConsumablePrice(int consumablePrice) {
        this.consumablePrice = consumablePrice;
        return this;
    }

    public ConsumableBuilder setConsumableDate(String consumableDate) {
        this.consumableDate = consumableDate;
        return this;
    }

    public ConsumableBuilder setConsumableCount(int consumableCount) {
        this.consumableCount = consumableCount;
        return this;
    }
}
