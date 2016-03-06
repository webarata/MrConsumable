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

import link.arata.dro.mrconsumable.dao.impl.ConsumableDaoImpl;
import link.arata.dro.mrconsumable.entity.Consumable;
import link.arata.dro.mrconsumable.helper.AppOpenHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ConsumableDaoTest {
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
        String sql = "INSERT INTO consumable(consumable_name, consumable_furigana, " +
            "consumable_note, consumable_price, consumable_date, consumable_count) " +
            "VALUES(?, ?, ?, ?, ?, ?)";

        String[][] params = {
            {"リビング電球", "リビングデンキュウ", "40W E26", "200", "2015/07/12", "2"},
            {"単三電池", "タンサンデンチ", "", "150", "2015/07/10", "8"},
            {"トイレットペーパー", "トイレットペーパー", "", "-1", "", "-1"}};
        for (String[] param : params) {
            db.execSQL(sql, param);
        }
    }

    @Test
    public void データの登録のテスト() throws Exception {
        prepareData();

        Consumable consumable = new ConsumableBuilder().createConsumable();
        consumable.setConsumableName("単四電池");
        consumable.setConsumableFurigana("タンヨンデンチ");
        consumable.setConsumableNote("備考");
        consumable.setConsumablePrice(195);
        consumable.setConsumableDate("20150723");
        consumable.setConsumableCount(1);

        ConsumableDao consumableDao = new ConsumableDaoImpl(db);
        long id = consumableDao.insert(consumable);
        assertThat("IDがインクリメントされている", id, is(4L));

        Cursor cursor = db.rawQuery("SELECT * FROM consumable " +
            "WHERE consumable_name = '単四電池'", null);
        if (!cursor.moveToNext()) {
            fail("追加したレコードが存在しない");
        }

        assertThat("IDが登録されている", cursor.getLong(0), is(4L));
        assertThat("消耗品名が登録されている", cursor.getString(1), is("単四電池"));
        assertThat("消耗品名のフリガナが登録されている", cursor.getString(2), is("タンヨンデンチ"));
        assertThat("消耗品の備考が登録されている", cursor.getString(3), is("備考"));
        assertThat("消耗品の最安値が登録されている", cursor.getInt(4), is(195));
        assertThat("消耗品の購入日が登録されている", cursor.getString(5), is("20150723"));
        assertThat("消耗品の購入個数が登録されている", cursor.getInt(6), is(1));

        cursor.close();
    }

    @Test
    public void データの初期登録のテスト() throws Exception {
        prepareData();

        Consumable consumable = new ConsumableBuilder().createConsumable();
        consumable.setConsumableName("単四電池");
        consumable.setConsumableFurigana("タンヨンデンチ");
        consumable.setConsumableNote("備考");

        ConsumableDao consumableDao = new ConsumableDaoImpl(db);
        long id = consumableDao.insertInit(consumable);
        assertThat("IDがインクリメントされている", id, is(4L));

        Cursor cursor = db.rawQuery("SELECT * FROM consumable " +
            "WHERE consumable_name = '単四電池'", null);
        if (!cursor.moveToNext()) {
            fail("追加したレコードが存在しない");
        }

        assertThat("IDが登録されている", cursor.getLong(0), is(4L));
        assertThat("消耗品名が登録されている", cursor.getString(1), is("単四電池"));
        assertThat("消耗品名のフリガナが登録されている", cursor.getString(2), is("タンヨンデンチ"));
        assertThat("消耗品の備考が登録されている", cursor.getString(3), is("備考"));
        assertThat("消耗品の最安値が登録されていない", cursor.getInt(4), is(-1));
        assertThat("消耗品の購入日が登録されていない", cursor.getString(5), is(""));
        assertThat("消耗品の購入個数が登録されていない", cursor.getInt(6), is(-1));

        cursor.close();
    }

    @Test
    public void 全件取得が正しく動作すること() throws Exception {
        prepareData();

        ConsumableDao consumableDao = new ConsumableDaoImpl(db);
        List<Consumable> consumableList = consumableDao.selectAll();

        assertThat("検索結果の数が正しいこと", consumableList.size(), is(3));

        // orderも正しいか確認する
        Consumable consumable1 = consumableList.get(0);
        assertThat(consumable1.getConsumableId(), is(2L));
        assertThat(consumable1.getConsumableName(), is("単三電池"));
        assertThat(consumable1.getConsumableFurigana(), is("タンサンデンチ"));
        assertThat(consumable1.getConsumableNote(), is(""));
        assertThat(consumable1.getConsumablePrice(), is(150));
        assertThat(consumable1.getConsumableDate(), is("2015/07/10"));
        assertThat(consumable1.getConsumableCount(), is(8));

        Consumable consumable2 = consumableList.get(1);
        assertThat(consumable2.getConsumableId(), is(3L));
        assertThat(consumable2.getConsumableName(), is("トイレットペーパー"));
        assertThat(consumable2.getConsumableFurigana(), is("トイレットペーパー"));
        assertThat(consumable2.getConsumableNote(), is(""));
        assertThat(consumable2.getConsumablePrice(), is(-1));
        assertThat(consumable2.getConsumableDate(), is(""));
        assertThat(consumable2.getConsumableCount(), is(-1));

        Consumable consumable3 = consumableList.get(2);
        assertThat(consumable3.getConsumableId(), is(1L));
        assertThat(consumable3.getConsumableName(), is("リビング電球"));
        assertThat(consumable3.getConsumableFurigana(), is("リビングデンキュウ"));
        assertThat(consumable3.getConsumableNote(), is("40W E26"));
        assertThat(consumable3.getConsumablePrice(), is(200));
        assertThat(consumable3.getConsumableDate(), is("2015/07/12"));
        assertThat(consumable3.getConsumableCount(), is(2));
    }
}
