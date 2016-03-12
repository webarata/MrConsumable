package link.arata.dro.mrconsumable.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import link.arata.common.util.IoUtil;
import link.arata.dro.mrconsumable.dao.ConsumableDao;
import link.arata.dro.mrconsumable.dao.impl.ConsumableDaoImpl;
import link.arata.dro.mrconsumable.entity.Consumable;
import link.arata.dro.mrconsumable.entity.ConsumablePic;
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

    public void registerConsumable(Context context, @NonNull Consumable consumable, @Nullable String imagePath) {
        AppOpenHelper appOpenHelper = new AppOpenHelper(context);
        SQLiteDatabase db = appOpenHelper.getWritableDatabase();
        ConsumableDao consumableDao = new ConsumableDaoImpl(db);

        long consumableId = consumableDao.insertInit(consumable);

        if (imagePath != null) {
//            ConsumablePicDao consumablePicDao = new ConsumablePicDaoImpl(db);
//            ConsumablePic consumablePic = new ConsumablePic();
//            consumablePic.setConsumableId(consumableId);
//            InputStream is = null;
//            try {
//                is = new FileInputStream(new File(imagePath));
//                consumablePic.setConsumablePic(IoUtil.readByteAndClose(is));
//                consumablePicDao.insert(consumablePic);
//            } catch (IOException e) {
//            }
        }

        db.close();
        appOpenHelper.close();

        notifyObserver(ModelEvent.FINISH_REGISTER_CONSUMABLE);
    }
}
