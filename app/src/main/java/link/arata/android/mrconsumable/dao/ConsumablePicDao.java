package link.arata.android.mrconsumable.dao;

import android.support.annotation.Nullable;

import net.kuwalab.android.consumable.entity.ConsumablePic;

public interface ConsumablePicDao {
    long insert(ConsumablePic consumablePic);

    @Nullable
    ConsumablePic selectByKey(long consumablePicId);
}
