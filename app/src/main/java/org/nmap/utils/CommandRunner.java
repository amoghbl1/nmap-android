package org.nmap.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.nmap.nmap_android.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandRunner {

    private String DEBUG_TAG = "myTag";

    public static String execCommand(String command, File currentDirectory) throws IOException, InterruptedException{
        Process process = Runtime.getRuntime().exec(command, null, currentDirectory);
        process.waitFor();
        BufferedReader readerInputStream = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        BufferedReader readerErrorStream = new BufferedReader(
                new InputStreamReader(process.getErrorStream())
        );
        int read;
        char[] buf = new char[4096];
        StringBuffer output = new StringBuffer();
        StringBuffer error = new StringBuffer();
        while ((read = readerInputStream.read(buf)) > 0) {
            output.append(buf, 0, read);
        }
        while((read = readerErrorStream.read(buf)) > 0) {
            error.append(buf, 0, read);
        }
        readerInputStream.close();
        readerErrorStream.close();
        Log.d("myTag", error.toString());
        return output.toString() + error.toString();
    }
}