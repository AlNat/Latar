package com.dk.shoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.view.View;

import com.dk.shoppinglist.R;

public class ListDbAdapter {
 
    // ���� ���� ������
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DESCRIPTION = "description";
    private static final String DATABASE_TABLE = "list";
    private Context context;
    private SQLiteDatabase database;
    private ListDatabaseHelper dbHelper;
 
    public ListDbAdapter(Context context) {
        this.context = context;
    }
 
    public ListDbAdapter open() throws SQLException {
        dbHelper = new ListDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {
        dbHelper.close();
    }
 
    /**
     * ������� ����� ������� ������ �������. ���� ������ ������� - ������������ ����� ������ rowId
     * ����� -1
     */
    public long createList(String category, String summary, String description, String price) {
        ContentValues initialValues = createContentValues(category, summary,
                description, price);
 
        return database.insert(DATABASE_TABLE, null, initialValues);
    }
 
    /**
     * �������� ������
     */
    public boolean updateList(long rowId, String category, String summary,
            String description, String price) {
        ContentValues updateValues = createContentValues(category, summary,
                description, price);
 
        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
                + rowId, null) > 0;
    }
 

    /**
     * ������� ������� ������
     */
    public boolean deleteList(long rowId) {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public void deleteAllList() {
        database.delete(DATABASE_TABLE, null, null);
    }
 
    /**
     * ���������� ������ �� ����� ���������� ������ �������
     *
     * @return ������ � ������������ ���� �������
     */
    public Cursor fetchAllList() {
        return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
                KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION, KEY_PRICE }, null, null, null,
                null, null, null);
    }
 
    /**
     * ���������� ������, ������������������ �� ��������� ������
     */
    public Cursor fetchList(long rowId) throws SQLException {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION, KEY_PRICE },
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public long getSum(){
        long res=0;
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{"SUM("+KEY_PRICE+")"}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            res= cursor.getLong(0);
        }
        return res;
    }

 
    private ContentValues createContentValues(String category, String summary,
            String description, String price) {
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category);
        values.put(KEY_SUMMARY, summary);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_PRICE, price);
        return values;
    }
}