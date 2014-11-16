package de.vogella.android.todos;

import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.app.Activity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Nas extends Activity {

  private SeekBar seekBar;

  @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.nastroik);
  
  seekBar = (SeekBar) findViewById(R.id.seekBar1);
  seekBar.setMax(255);

   float curBrightnessValue = 0;
  try {
   curBrightnessValue = android.provider.Settings.System.getInt(
     getContentResolver(),
     android.provider.Settings.System.SCREEN_BRIGHTNESS);
  } catch (SettingNotFoundException e) {
   e.printStackTrace();
  }

   int screen_brightness = (int) curBrightnessValue;
  seekBar.setProgress(screen_brightness);
  seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   int progress = 0;

    @Override
   public void onProgressChanged(SeekBar seekBar, int progresValue,
     boolean fromUser) {
    progress = progresValue;
   }

    @Override
   public void onStartTrackingTouch(SeekBar seekBar) {
    // Do something here,
    // if you want to do anything at the start of
    // touching the seekbar
   }

    @Override
   public void onStopTrackingTouch(SeekBar seekBar) {
    android.provider.Settings.System.putInt(getContentResolver(),
      android.provider.Settings.System.SCREEN_BRIGHTNESS,
      progress);
   }
  });
 }

  @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.menu, menu);
  return true;
 }

}