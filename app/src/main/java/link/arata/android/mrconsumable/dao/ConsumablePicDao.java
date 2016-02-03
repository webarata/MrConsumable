package link.arata.android.mrconsumable.dao;

import android.support.annotation.Nullable;

import link.arata.android.mrconsumable.entity.ConsumablePic;

public interface ConsumablePicDao {
    long insert(ConsumablePic consumablePic);

    @Nullable
    ConsumablePic selectByKey(long consumablePicId);
}
