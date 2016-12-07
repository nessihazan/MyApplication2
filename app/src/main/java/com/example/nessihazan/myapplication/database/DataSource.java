package com.example.nessihazan.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nessihazan.myapplication.model.DataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nessihazan on 07/12/2016.
 */

public class DataSource {
  private Context mContext;
    private  SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;
    public DataSource(Context Context) {
        this.mContext = Context;
    mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();

    }
public void open(){
    mDatabase = mDbHelper.getWritableDatabase();


}
public  void close(){
    mDbHelper.close();
}
public DataItem createItem(DataItem item){
    ContentValues values = item.toValues();
    mDatabase.insert(ItemsTable.TABLE_ITEMS,null,values);
    return item;


}
public  long getDataItemsCount(){
    return DatabaseUtils.queryNumEntries(mDatabase,ItemsTable.TABLE_ITEMS);
}

    public void seedDatabase(List<DataItem> dataItemList) {
        long numItems = getDataItemsCount();
        if (numItems == 0) {
            for (DataItem item :
                    dataItemList) {
                try {
                    createItem(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<DataItem> getAllItems() {
        List<DataItem> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                null, null, null, null, ItemsTable.COLUMN_NAME);

        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setItemName(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION)));
            item.setCategory(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            item.setSortPostion(cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setPrice(cursor.getDouble(
                    cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
            item.setImage(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
            dataItems.add(item);
        }
        cursor.close();
        return dataItems;
    }}


