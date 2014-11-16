package com.dk.shoppinglist;

import android.app.Activity;
import android.os.Bundle;


public class Stat extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.stat);
    getActionBar().hide();
  }
}