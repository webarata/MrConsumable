package link.arata.dro.mrconsumable.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import link.arata.dro.mrconsumable.dao.impl.ShopDaoImpl;
import link.arata.dro.mrconsumable.entity.Shop;
import link.arata.dro.mrconsumable.helper.AppOpenHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ShopDaoTest {
    private AppOpenHelper helper;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        helper = new AppOpenHelper(new RenamingDelegatingContext(context, "test_"));

        db = helper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        db.close();
        helper.close();
    }

    private void prepareData() {
        String sql = "INSERT INTO shop(shop_name, shop_furigana) VALUES(?, ?)";

        String[][] params = {{"ヨトハシカメラ", "ヨトハシカメラ"}, {"やまた電気", "ヤマタデンキ"}};
        for (String[] param : params) {
            db.execSQL(sql, param);
        }
    }

    @Test
    public void データの登録のテスト() throws Exception {
        prepareData();

        Shop shop = new Shop();
        shop.setShopName("大カメラ");
        shop.setShopFurigana("ピックカメラ");

        ShopDao shopDao = new ShopDaoImpl(db);
        long id = shopDao.insert(shop);
        assertThat("IDがインクリメントされている", id, is(3L));

        Cursor cursor = db.rawQuery("SELECT * FROM shop WHERE shop_name = '大カメラ'", null);
        if (!cursor.moveToNext()) {
            fail("追加したレコードが存在しない");
        }

        assertThat("IDが登録されている", cursor.getLong(0), is(3L));
        assertThat("店名が登録されている", cursor.getString(1), is("大カメラ"));
        assertThat("店名のふりがなが登録されている", cursor.getString(2), is("ピックカメラ"));

        cursor.close();
    }

    @Test
    public void 全件取得が正しく動作すること() throws Exception {
        prepareData();

        ShopDao shopDao = new ShopDaoImpl(db);
        List<Shop> shopList = shopDao.selectAll();
        assertThat("検索結果の数が正しいこと", shopList.size(), is(2));

        // orderも正しいか確認する
        Shop shop1 = shopList.get(0);
        assertThat(shop1.getShopId(), is(2L));
        assertThat(shop1.getShopName(), is("やまた電気"));
        assertThat(shop1.getShopFurigana(), is("ヤマタデンキ"));

        Shop shop2 = shopList.get(1);
        assertThat(shop2.getShopId(), is(1L));
        assertThat(shop2.getShopName(), is("ヨトハシカメラ"));
        assertThat(shop2.getShopFurigana(), is("ヨトハシカメラ"));
    }
}
