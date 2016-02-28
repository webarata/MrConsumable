package link.arata.dro.mrconsumable.dao;


import java.util.List;

import link.arata.dro.mrconsumable.entity.Shop;

public interface ShopDao {
    long insert(Shop shop);

    List<Shop> selectAll();
}
