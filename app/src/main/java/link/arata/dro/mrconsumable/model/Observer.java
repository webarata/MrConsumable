package link.arata.dro.mrconsumable.model;

/**
 * Observer
 * @author arata
 */
public interface Observer {
    /**
     * イベントを通知する
     * @param modelEvent 通知するイベント
     */
    void notify(ModelEvent modelEvent);
}
