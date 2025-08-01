package com.example.ispit;

import android.app.Service;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API extends AsyncTask<String, Void , String> {



    @Override
    protected String doInBackground(String... params) {
        String data="";

        HttpURLConnection httpURLConnection = null;
        try{

            httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
  //        httpURLConnection.connect();
//          connection.getOutputStream().write(myurldata.getBytes());
          httpURLConnection.getOutputStream().write(("name=" + params[1]).getBytes());
//            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//            wr.writeBytes("PostData=" + params[1]);
//            wr.flush();
//            wr.close();



            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("milan", result); // this is expecting a response code to be sent from your server upon receiving the POST data
    }
}
