package com.dk.shoppinglist;

import android.app.Activity;
import android.os.Bundle;


public class Arhiv extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActionBar().hide();
    setContentView(R.layout.arhiv);
  }
}