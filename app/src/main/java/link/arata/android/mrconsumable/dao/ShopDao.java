package link.arata.android.mrconsumable.dao;


import java.util.List;

import link.arata.android.mrconsumable.entity.Shop;

public interface ShopDao {
    long insert(Shop shop);

    List<Shop> selectAll();
}
