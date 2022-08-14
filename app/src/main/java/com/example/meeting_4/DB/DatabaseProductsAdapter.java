package com.example.meeting_4.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.meeting_4.Models.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProductsAdapter {

    private DatabaseProductsHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseProductsAdapter(Context context){
        dbHelper = new DatabaseProductsHelper(context.getApplicationContext());
    }

    public DatabaseProductsAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseProductsHelper.ID, DatabaseProductsHelper.NAME, DatabaseProductsHelper.COUNT, DatabaseProductsHelper.PRICE};
        return  database.query(DatabaseProductsHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<ProductEntity> getEntities(){
        ArrayList<ProductEntity> entities = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseProductsHelper.ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseProductsHelper.NAME));
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex(DatabaseProductsHelper.COUNT));
            @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(DatabaseProductsHelper.PRICE));
            entities.add(new ProductEntity(id, name, count, price));
        }
        cursor.close();
        return  entities;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseProductsHelper.TABLE);
    }

    public ProductEntity getEntity(long id){
        ProductEntity entity = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseProductsHelper.TABLE, DatabaseProductsHelper.ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseProductsHelper.NAME));
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex(DatabaseProductsHelper.COUNT));
            @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(DatabaseProductsHelper.PRICE));
            entity = new ProductEntity(id, name, count, price);
        }
        cursor.close();
        return  entity;
    }

    public long insert(ProductEntity entity){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseProductsHelper.NAME, entity.getName());
        cv.put(DatabaseProductsHelper.COUNT, entity.getCount());
        cv.put(DatabaseProductsHelper.PRICE, entity.getPrice());

        return  database.insert(DatabaseProductsHelper.TABLE, null, cv);
    }

    public long delete(long entityId){

        String whereClause = dbHelper.ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(entityId)};
        return database.delete(DatabaseProductsHelper.TABLE, whereClause, whereArgs);
    }

    public long update(ProductEntity entity){

        String whereClause = DatabaseProductsHelper.ID + "=" + entity.getId();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseProductsHelper.NAME, entity.getName());
        cv.put(DatabaseProductsHelper.COUNT, entity.getCount());
        cv.put(DatabaseProductsHelper.PRICE, entity.getPrice());
        return database.update(DatabaseProductsHelper.TABLE, cv, whereClause, null);
    }
}