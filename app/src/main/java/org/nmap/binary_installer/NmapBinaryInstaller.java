package org.nmap.binary_installer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.util.Log;

import org.nmap.nmap_android.R;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class NmapBinaryInstaller {
    Context context;

    String DEBUG_TAG = "myTag";

    public NmapBinaryInstaller (Context context) {
        this.context = context;
    }
    /*

     */
    public boolean installResources() {
        InputStream inputStream;
        OutputStream outputStream;
        File outFile;
        File appBinHome = context.getDir("bin", Context.MODE_MULTI_PROCESS);
        Resources resources = context.getResources();
        byte[] buffer = new byte[1024];
        int bytecount;

        try {

            inputStream = resources.openRawResource(R.raw.nmap);
            outFile = new File(appBinHome, "nmap");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_os_db);
            outFile = new File(appBinHome, "nmap-os-db");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_payloads);
            outFile = new File(appBinHome, "nmap-payloads");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_protocols);
            outFile = new File(appBinHome, "nmap-protocols");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_rpc);
            outFile = new File(appBinHome, "nmap-rpc");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_service_probes);
            outFile = new File(appBinHome, "nmap-service-probes");
            moveBinaryRawResourceToFile(inputStream, outFile);

            inputStream = resources.openRawResource(R.raw.nmap_services);
            outFile = new File(appBinHome, "nmap-services");
            moveBinaryRawResourceToFile(inputStream, outFile);
        }
        catch (Exception e) {
            Log.d(DEBUG_TAG, e.getMessage());
        }


        return true;
    }

    /*
    Write from an input stream to an output file
    */
    private void moveBinaryRawResourceToFile(InputStream inputStream, File outFile) throws IOException {
        byte[] buf = new byte[1024];
        int bytecount;
        OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
        while((bytecount = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytecount);
        }
        inputStream.close();
        outputStream.close();
    }
}