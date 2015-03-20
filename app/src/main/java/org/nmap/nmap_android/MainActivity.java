package org.nmap.nmap_android;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.nmap.binary_installer.NmapBinaryInstaller;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    String DEFAULT_SHARED_PREFERENCES = "mySharedPrefs";
    String firstStartPref = "firstStart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean firstInstall = true;
        SharedPreferences mySharedPreferences = getSharedPreferences(DEFAULT_SHARED_PREFERENCES, MODE_MULTI_PROCESS);
        firstInstall = mySharedPreferences.getBoolean(firstStartPref, true);
        if(firstInstall) {
            NmapBinaryInstaller installer = new NmapBinaryInstaller(getApplicationContext(), new File("/sdcard/nmap"));
            installer.installResources();
        }
        Button scan = (Button)findViewById(R.id.scan_BT);
        EditText flags = (EditText)findViewById(R.id.flags_ET);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
