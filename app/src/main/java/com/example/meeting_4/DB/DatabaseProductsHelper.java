package com.example.meeting_4.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseProductsHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "products";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String COUNT = "count";
    public static final String PRICE = "price";

    public DatabaseProductsHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
                + " TEXT NOT NULL, " + COUNT + " INTEGER DEFAULT 1, " + PRICE + " DOUBLE);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + NAME
                + ", " + COUNT  + ", " + PRICE + ") VALUES ('iPhone 11', 1, 15990);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + NAME
                + ", " + COUNT  + ", " + PRICE + ") VALUES ('iPhone 11 Pro', 2, 22990);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + NAME
                + ", " + COUNT  + ", " + PRICE + ") VALUES ('iPhone 11 Pro Max', 3, 32990);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
