package link.arata.android.mrconsumable.dao;

import java.util.List;

import link.arata.android.mrconsumable.entity.Consumable;

public interface ConsumableDao {
    long insert(Consumable consumable);

    /**
     * 初期のinsert。<br>
     * 消耗品名と備考のみ登録する
     *
     * @param consumable
     * @return
     */
    long insertInit(Consumable consumable);

    List<Consumable> selectAll();
}
