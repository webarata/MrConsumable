package link.arata.android.mrconsumable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import net.kuwalab.android.consumable.dao.impl.ConsumablePicDaoImpl;
import net.kuwalab.android.consumable.entity.ConsumablePic;
import net.kuwalab.android.consumable.helper.AppOpenHelper;
import net.kuwalab.android.consumable.util.IoUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ConsumablePicDaoTest {
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

    @Test
    public void test() throws Exception {
        prepareData();
    }

    private void prepareData() throws Exception {
        String sql = "INSERT INTO consumable_pic(consumable_id, consumable_pid) " +
            "VALUES(?, ?)";

        ContentValues values = new ContentValues();
        values.put(ConsumablePic.CONSUMABLE_ID, 1);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        InputStream is = context.getAssets().open("test1.jpg");
        values.put(ConsumablePic.CONSUMABLE_PIC, IoUtil.readByteAndClose(is));

        db.insert(ConsumablePic.NAME, null, values);
    }

    @Test
    public void データの登録のテスト() throws Exception {
        prepareData();

        ConsumablePic consumablePic = new ConsumablePic();
        consumablePic.setConsumableId(3L);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        InputStream is = context.getAssets().open("test2.jpg");
        consumablePic.setConsumablePic(IoUtil.readByteAndClose(is));

        ConsumablePicDao consumablePicDao = new ConsumablePicDaoImpl(db);
        long id = consumablePicDao.insert(consumablePic);
        assertThat("IDがインクリメントされている", id, is(2L));

        Cursor cursor = db.rawQuery("SELECT * FROM consumable_pic " +
            "WHERE consumable_pic_id = '2'", null);
        if (!cursor.moveToNext()) {
            fail("追加したレコードが存在しない");
        }

        assertThat(cursor.getLong(0), is(2L));
        assertThat(cursor.getLong(1), is(3L));

        byte[] blob = cursor.getBlob(2);

        assertThat("画像データがnullでないこと", blob, is(notNullValue()));
        assertThat("画像データのサイズが正しいこと", blob.length, is(3392));

        cursor.close();
    }

    @Test
    public void キーによる取得が正しく動作すること() throws Exception {
        prepareData();

        ConsumablePicDao consumablePicDao = new ConsumablePicDaoImpl(db);
        ConsumablePic consumablePic = consumablePicDao.selectByKey(1);

        assertThat("検索結果がnullでないこと", consumablePic, is(notNullValue()));

        assertThat(consumablePic.getConsumablePicId(), is(1L));
        assertThat(consumablePic.getConsumableId(), is(1L));

        byte[] blob = consumablePic.getConsumablePic();

        assertThat("画像データがnullでないこと", blob, is(notNullValue()));
        assertThat("画像データのサイズが正しいこと", blob.length, is(8778));
    }
}
