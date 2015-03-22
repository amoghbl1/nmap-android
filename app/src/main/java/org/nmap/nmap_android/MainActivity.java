package org.nmap.nmap_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.nmap.binary_installer.NmapBinaryInstaller;
import org.nmap.utils.CommandRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    String DEBUG_TAG = "myTag";

    String DEFAULT_SHARED_PREFERENCES = "mySharedPrefs";
    String firstStartPref = "firstStart";

    public static File appBinHome;
    String NMAP_COMMAND = "./nmap ";

    public static TextView scanResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean firstInstall = true;
        appBinHome = getDir("bin", Context.MODE_MULTI_PROCESS);

        SharedPreferences mySharedPreferences = getSharedPreferences(DEFAULT_SHARED_PREFERENCES, MODE_MULTI_PROCESS);
        firstInstall = mySharedPreferences.getBoolean(firstStartPref, true);
        if(firstInstall) {
            NmapBinaryInstaller installer = new NmapBinaryInstaller(getApplicationContext());
            installer.installResources();
            Log.d(DEBUG_TAG, "Installing binaries");
            // TODO: Write some test code to see if the binaries are placed correctly and have the right permissions!
            mySharedPreferences.edit().putBoolean(firstStartPref, false).commit();
        }
        Button scan = (Button)findViewById(R.id.scan_BT);
        final EditText flags = (EditText)findViewById(R.id.flags_ET);
        scanResult = (TextView)findViewById(R.id.scan_output_TV);

        scanResult.setMovementMethod(new ScrollingMovementMethod());

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = flags.getText().toString();
                new AsyncCommandExecutor().execute( NMAP_COMMAND + f);
            }
        });

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

    public class AsyncCommandExecutor extends AsyncTask<String, Void, Void> {

        public String returnOutput;
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            this.progressDialog.setTitle("NMAP");
            this.progressDialog.setMessage("Scanning...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
            return;
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                this.returnOutput = CommandRunner.execCommand(params[0], MainActivity.appBinHome.getAbsoluteFile());
            } catch (IOException e) {
                this.returnOutput = "IOException while trying to scan!";
                Log.d(DEBUG_TAG, e.getMessage());
            } catch (InterruptedException e) {
                this.returnOutput = "Nmap Scan Interrupted!";
                Log.d(DEBUG_TAG, e.getMessage());
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            MainActivity.scanResult.setText(returnOutput);
            if(this.progressDialog.isShowing())
                this.progressDialog.dismiss();
        }
    }
}