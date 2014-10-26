package com.dk.shoppinglist;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.dk.shoppinglist.database.ListDbAdapter;
 
public class ListOverview extends ListActivity {
    private ListDbAdapter dbHelper;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private Cursor cursor;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.getListView().setDividerHeight(2);
        dbHelper = new ListDbAdapter(this);
        dbHelper.open();
        fillData();
        registerForContextMenu(getListView());
    }
 
    // ������� ����, ���������� �� XML-�����
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }
 
    // ������� �� ����� ����
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case R.id.insert:
            createList();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.insert:
            createList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case DELETE_ID:
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                    .getMenuInfo();
            dbHelper.deleteList(info.id);
            fillData();
            return true;
        }
        return super.onContextItemSelected(item);
    }
 
    private void createList() {
        Intent i = new Intent(this, ListDetails.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
 
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ListDetails.class);
        i.putExtra(ListDbAdapter.KEY_ROWID, id);
        // �������� ������ ��������� ���� ����� ������� � ������� ����� ������
        startActivityForResult(i, ACTIVITY_EDIT);
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
 
    }
 
    private void fillData() {
        cursor = dbHelper.fetchAllList();
        startManagingCursor(cursor);
 
        String[] from = new String[] { ListDbAdapter.KEY_SUMMARY, ListDbAdapter.KEY_PRICE };
        int[] to = new int[] { R.id.label, R.id.price  };
        // ������ �������� ������� ������� � ��������� ��� ��� ����������� ����� ������
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
                R.layout.list_row, cursor, from, to);
        setListAdapter(notes);
    }
 
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}