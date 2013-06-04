package com.gpeal.rpics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utils {
    public static String TAG = "rpics";


    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static String httpGet(String sUrl) {
        try {
            URL url = new URL(sUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return readStream(con.getInputStream());
        } catch (IOException e) {
            Log.e(TAG, "httpGet IOException: " + e.getMessage());
            return "";
        }
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "readStream IOException: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "readStream IOException 2: " + e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}
