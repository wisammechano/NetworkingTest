package com.recoded.networkingtest;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wisam on Oct 22 17.
 */
class GithubLoaderAsync extends AsyncTask<Object, Object, Object> {

    private final Context mContext;

    public GithubLoaderAsync(Context c) {
        this.mContext = c;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        StringBuilder json = new StringBuilder();
        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL("https://api.github.com/search/repositories?q=android+json&sort=updated&order=asc");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setConnectTimeout(10000);
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.connect();
            inputStream = httpUrlConnection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputReader);
            String line = bufferReader.readLine();
            while (line != null) {
                json.append(line);
                line = bufferReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpUrlConnection != null) httpUrlConnection.disconnect();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }

    @Override
    protected void onPostExecute(Object o) {
        String json = o.toString();
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Response response = gson.fromJson(json, Response.class);
            ((MainActivity) mContext).updateUI(response);
        }
    }
}
