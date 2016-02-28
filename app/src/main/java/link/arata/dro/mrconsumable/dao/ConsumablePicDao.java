package link.arata.dro.mrconsumable.dao;

import android.support.annotation.Nullable;

import link.arata.dro.mrconsumable.entity.ConsumablePic;

public interface ConsumablePicDao {
    long insert(ConsumablePic consumablePic);

    @Nullable
    ConsumablePic selectByKey(long consumablePicId);
}
