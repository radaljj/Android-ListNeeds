package com.example.listneeds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Baza.DATABASE_NAME, null, Baza.DATABASE_VERSION);
        this.context=context;
    }

    //We create our table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Baza.TABLE_NAME + "("
                + Baza.KEY_ID + " INTEGER PRIMARY KEY,"
                + Baza.KEY_NAME + " TEXT,"
                + Baza.KEY_DESCRIPTION + " TEXT,"
                + Baza.KEY_QUANTITY + " INTEGER,"
                + Baza.KEY_PRICE + " INTEGER,"
                + Baza.KEY_DATE + " LONG);";

        db.execSQL(CREATE_BABY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+ Baza.TABLE_NAME);
        onCreate(db);


    }


    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Baza.KEY_NAME, item.getName());
        values.put(Baza.KEY_DESCRIPTION, item.getDescription());
        values.put(Baza.KEY_DATE, java.lang.System.currentTimeMillis());
        values.put(Baza.KEY_PRICE, item.getAppPrice());
        values.put(Baza.KEY_QUANTITY, item.getQuantity());


        db.insert(Baza.TABLE_NAME, null, values);
        Log.d("DBHandler", "Dodato u bazu: " + "item added");
        db.close(); //closing db connection!



    }


    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Baza.TABLE_NAME,
                new String[]{ Baza.KEY_ID, Baza.KEY_NAME, Baza.KEY_PRICE,Baza.KEY_DESCRIPTION,Baza.KEY_QUANTITY,Baza.KEY_DATE},
                Baza.KEY_ID +"=?",new String[]{String.valueOf(id)},
                null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Baza.KEY_ID))));
        item.setName(cursor.getString(cursor.getColumnIndex(Baza.KEY_NAME)));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(Baza.KEY_QUANTITY)));
        item.setAppPrice(cursor.getInt(cursor.getColumnIndex(Baza.KEY_PRICE)));
        item.setDescription(cursor.getString(cursor.getColumnIndex(Baza.KEY_DESCRIPTION)));



        DateFormat dateFormat= DateFormat.getInstance();
        String datum=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Baza.KEY_DATE)))
        .getTime());
        item.setDate(datum);


        return item;
    }

    //Get all items
    //Get all Items
    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Baza.TABLE_NAME,
                new String[]{Baza.KEY_ID,
                        Baza.KEY_NAME,
                        Baza.KEY_DESCRIPTION,
                        Baza.KEY_QUANTITY,
                        Baza.KEY_PRICE,
                        Baza.KEY_DATE},
                null, null, null, null,
                Baza.KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Baza.KEY_ID))));
                item.setName(cursor.getString(cursor.getColumnIndex(Baza.KEY_NAME)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(Baza.KEY_DESCRIPTION)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(Baza.KEY_QUANTITY)));
                item.setAppPrice(cursor.getInt(cursor.getColumnIndex(Baza.KEY_PRICE)));

                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Baza.KEY_DATE)))
                        .getTime()); // Feb 23, 2020
                item.setDate(formattedDate);

                //Add to arraylist
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;

    }


    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Baza.KEY_NAME, item.getName());
        values.put(Baza.KEY_DATE, java.lang.System.currentTimeMillis());
        values.put(Baza.KEY_QUANTITY, item.getQuantity());
        values.put(Baza.KEY_DESCRIPTION, item.getDescription());
        values.put(Baza.KEY_PRICE, item.getAppPrice());


        //update the row
        //update(tablename, values, where id = 43)
        return db.update(Baza.TABLE_NAME, values, Baza.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Baza.TABLE_NAME, Baza.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT * FROM " + Baza.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }
}
