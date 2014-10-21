package de.vogella.android.todos.database;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
 
public class TodoDbAdapter {
 
    // ���� ���� ������
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_DESCRIPTION = "description";
    private static final String DATABASE_TABLE = "todo";
    private Context context;
    private SQLiteDatabase database;
    private TodoDatabaseHelper dbHelper;
 
    public TodoDbAdapter(Context context) {
        this.context = context;
    }
 
    public TodoDbAdapter open() throws SQLException {
        dbHelper = new TodoDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {
        dbHelper.close();
    }
 
    /**
     * ������� ����� ������� ������ ���. ���� ������ ������� - ������������ ����� ������ rowId
     * ����� -1
     */
    public long createTodo(String category, String summary, String description) {
        ContentValues initialValues = createContentValues(category, summary,
                description);
 
        return database.insert(DATABASE_TABLE, null, initialValues);
    }
 
    /**
     * �������� ������
     */
    public boolean updateTodo(long rowId, String category, String summary,
            String description) {
        ContentValues updateValues = createContentValues(category, summary,
                description);
 
        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
                + rowId, null) > 0;
    }
 
    /**
     * ������� ������� ������
     */
    public boolean deleteTodo(long rowId) {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
 
    /**
     * ���������� ������ �� ����� ���������� ������ ���
     *
     * @return ������ � ������������ ���� �������
     */
    public Cursor fetchAllTodos() {
        return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
                KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION }, null, null, null,
                null, null);
    }
 
    /**
     * ���������� ������, ������������������ �� ��������� ������
     */
    public Cursor fetchTodo(long rowId) throws SQLException {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION },
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
 
    private ContentValues createContentValues(String category, String summary,
            String description) {
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category);
        values.put(KEY_SUMMARY, summary);
        values.put(KEY_DESCRIPTION, description);
        return values;
    }
}