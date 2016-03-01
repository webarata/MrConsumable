package link.arata.dro.mrconsumable.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import link.arata.dro.mrconsumable.dao.ConsumableDao;
import link.arata.dro.mrconsumable.dao.impl.ConsumableDaoImpl;
import link.arata.dro.mrconsumable.entity.Consumable;
import link.arata.dro.mrconsumable.helper.AppOpenHelper;

/**
 * モデル
 *
 * @author arata
 */
public class ConsumableModel {
    private static final ConsumableModel consumableModel = new ConsumableModel();

    private List<Observer> observerList = new ArrayList<>();

    private List<Consumable> consumableList;

    private ConsumableModel() {
    }

    /**
     * インスタンスの取得
     *
     * @return インスタンス
     */
    public static ConsumableModel getInstance() {
        return consumableModel;
    }

    private void notifyObserver(ModelEvent modelEvent) {
        for (Observer observer : observerList) {
            observer.notify(modelEvent);
        }
    }

    /**
     * observerの追加
     *
     * @param observer 追加するobserver
     */
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    /**
     * observerの削除
     *
     * @param observer 削除するobserver
     */
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 消耗品の一覧の取得
     * @return 消耗品の一覧
     */
    public List<Consumable> getConsumableList() {
        return consumableList;
    }

    /**
     * 消耗品一覧の取得。取得後はFINISH_FETCH_CONSUMABLE_LISTイベントが発生する
     * @param context コンテキスト
     */
    public void fetchConsumableList(Context context) {
        AppOpenHelper appOpenHelper = new AppOpenHelper(context);
        SQLiteDatabase db = appOpenHelper.getReadableDatabase();
        ConsumableDao consumableDao = new ConsumableDaoImpl(db);
        consumableList = consumableDao.selectAll();
        db.close();
        appOpenHelper.close();

        notifyObserver(ModelEvent.FINISH_FETCH_CONSUMABLE_LIST);
    }
}
