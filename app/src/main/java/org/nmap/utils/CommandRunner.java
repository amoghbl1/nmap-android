package org.nmap.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.nmap.nmap_android.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandRunner {

    private String DEBUG_TAG = "myTag";

    public static String execCommand(String command) throws IOException, InterruptedException{
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        int read;
        char[] buf = new char[4096];
        StringBuffer output = new StringBuffer();
        while ((read = reader.read(buf)) > 0) {
            output.append(buf, 0, read);
        }
        reader.close();
        return output.toString();
    }
}