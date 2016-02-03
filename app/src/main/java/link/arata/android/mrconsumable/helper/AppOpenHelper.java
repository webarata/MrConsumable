package link.arata.android.mrconsumable.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import link.arata.android.mrconsumable.entity.Consumable;
import link.arata.android.mrconsumable.entity.Shop;

public class AppOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app";

    private static final int LATEST_VERSION = 1;

    public AppOpenHelper(Context context) {
        this(context, LATEST_VERSION);
    }

    public AppOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Shop.NAME +"(" +
            Shop.SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Shop.SHOP_NAME + " TEXT NOT NULL, " +
            Shop.SHOP_FURIGANA + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Consumable.NAME + "(" +
            Consumable.CONSUMABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Consumable.CONSUMABLE_NAME + " TEXT NOT NULL, " +
            Consumable.CONSUMABLE_FURIGANA + " TEXT NOT NULL, " +
            Consumable.CONSUMABLE_NOTE + " TEXT DEFAULT '' NOT NULL, " +
            Consumable.CONSUMABLE_PRICE + " INTEGER DEFAULT -1 NOT NULL, " +
            Consumable.CONSUMABLE_DATE + " TEXT DEFAULT '' NOT NULL, " +
            Consumable.CONSUMABLE_COUNT + " INTEGER DEFAULT -1 NOT NULL)");

//        db.execSQL("CREATE TABLE " + ConsumablePic.NAME + "(" +
//            ConsumablePic.CONSUMABLE_PIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            ConsumablePic.CONSUMABLE_ID + " TEXT NOT NULL, " +
//            ConsumablePic.CONSUMABLE_PIC + " BLOB NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + ConsumablePic.NAME);
        db.execSQL("drop table " + Consumable.NAME);
        db.execSQL("drop table " + Shop.NAME);

        onCreate(db);
    }
}
