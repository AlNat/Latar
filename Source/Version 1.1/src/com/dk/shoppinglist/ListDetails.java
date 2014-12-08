package com.dk.shoppinglist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dk.shoppinglist.database.ListDbAdapter;

public class ListDetails extends Activity {
    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private ListDbAdapter mDbHelper;
    private Spinner mCategory;
    private EditText mPrice;
    private ListDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle bundle) {
    	getActionBar().hide();
        super.onCreate(bundle);
        mDbHelper = new ListDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.list_edit);
        mCategory = (Spinner) findViewById(R.id.category);
        mTitleText = (EditText) findViewById(R.id.list_edit_summary);
        mBodyText = (EditText) findViewById(R.id.list_edit_description);
        mPrice = (EditText) findViewById(R.id.list_edit_price);

        Button confirmButton = (Button) findViewById(R.id.list_edit_button);
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        mRowId = (bundle == null) ? null : (Long) bundle
                .getSerializable(ListDbAdapter.KEY_ROWID);
        if (extras != null) {
            mRowId = extras.getLong(ListDbAdapter.KEY_ROWID);
        }
        populateFields();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });

    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor list = mDbHelper.fetchList(mRowId);
            startManagingCursor(list);
            String category = list.getString(list
                    .getColumnIndexOrThrow(ListDbAdapter.KEY_CATEGORY));

            for (int i=0; i<mCategory.getCount();i++){

                String s = (String) mCategory.getItemAtPosition(i);
                Log.e(null, s +" " + category);
                if (s.equalsIgnoreCase(category)){
                    mCategory.setSelection(i);
                }
            }

            mTitleText.setText(list.getString(list
                    .getColumnIndexOrThrow(ListDbAdapter.KEY_SUMMARY)));
            mPrice.setText(list.getString(list
                    .getColumnIndexOrThrow(ListDbAdapter.KEY_PRICE)));
            mBodyText.setText(list.getString(list
                    .getColumnIndexOrThrow(ListDbAdapter.KEY_DESCRIPTION)));
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(ListDbAdapter.KEY_ROWID, mRowId);

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String category = (String) mCategory.getSelectedItem();
        String summary;
        if(mTitleText.getText().toString().equals("")){
            summary="Пустая покупка";
        }
        else{summary = mTitleText.getText().toString();};
        String description;
        if(mBodyText.getText().toString().equals("")){
            description = "0";
        }
        else{description = mBodyText.getText().toString();};
        String price;
                if(mPrice.getText().toString().equals("")){
                    price = "0";
                }
        else{price = mPrice.getText().toString();};

        if (mRowId == null) {
            long id = mDbHelper.createList(category, summary, description, price);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateList(mRowId, category, summary, description, price);
        }

    }
}