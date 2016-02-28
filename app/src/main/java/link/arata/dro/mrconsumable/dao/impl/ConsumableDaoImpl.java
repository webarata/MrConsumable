package link.arata.dro.mrconsumable.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import link.arata.dro.mrconsumable.dao.ConsumableDao;
import link.arata.dro.mrconsumable.entity.Consumable;

public class ConsumableDaoImpl implements ConsumableDao {
    private SQLiteDatabase db;

    public ConsumableDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insert(@NonNull Consumable consumable) {
        ContentValues values = new ContentValues();
        values.put(Consumable.CONSUMABLE_NAME, consumable.getConsumableName());
        values.put(Consumable.CONSUMABLE_FURIGANA, consumable.getConsumableFurigana());
        values.put(Consumable.CONSUMABLE_NOTE, consumable.getConsumableNote());
        values.put(Consumable.CONSUMABLE_PRICE, consumable.getConsumablePrice());
        values.put(Consumable.CONSUMABLE_DATE, consumable.getConsumableDate());
        values.put(Consumable.CONSUMABLE_COUNT, consumable.getConsumableCount());

        return db.insert(Consumable.NAME, "", values);
    }

    @Override
    public long insertInit(@NonNull Consumable consumable) {
        SQLiteStatement stmt =  db.compileStatement("INSERT INTO " + Consumable.NAME  + "(" +
                Consumable.CONSUMABLE_NAME + ", "+
                Consumable.CONSUMABLE_FURIGANA + ", "+
                Consumable.CONSUMABLE_NOTE + ") " +
                "VALUES(?, ?, ?)"
        );
        stmt.bindString(1, consumable.getConsumableName());
        stmt.bindString(2, consumable.getConsumableFurigana());
        stmt.bindString(3, consumable.getConsumableNote());

        return stmt.executeInsert();
    }

    @Override
    @NonNull
    public List<Consumable> selectAll() {
        List<Consumable> consumableList = new ArrayList<>();

        Cursor cursor = db.query(Consumable.NAME, null, null, null, null, null,
            Consumable.CONSUMABLE_FURIGANA);
        while (cursor.moveToNext()) {
            consumableList.add(toConsumable(cursor));
        }
        cursor.close();

        return consumableList;
    }

    @NonNull
    private Consumable toConsumable(@NonNull Cursor cursor) {
        Consumable consumable = new Consumable();
        consumable.setConsumableId(cursor.getInt(0));
        consumable.setConsumableName(cursor.getString(1));
        consumable.setConsumableFurigana(cursor.getString(2));
        consumable.setConsumableNote(cursor.getString(3));
        consumable.setConsumablePrice(cursor.getInt(4));
        consumable.setConsumableDate(cursor.getString(5));
        consumable.setConsumableCount(cursor.getInt(6));

        return consumable;
    }
}
