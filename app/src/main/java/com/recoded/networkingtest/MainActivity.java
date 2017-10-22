package com.recoded.networkingtest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recoded.networkingtest.databinding.ActivityMainBinding;
import com.recoded.networkingtest.databinding.ResultItemBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityBinding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityBinding.progressBar.animate();
        recyclerView = activityBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        (new GithubLoaderAsync()).execute();
    }

    public void updateUI(Response res) {

        RecyclerAdapter adapter = new RecyclerAdapter(this);
        for (ItemsItem item : res.getItems()) {
            adapter.addItem(item);
        }
        recyclerView.setAdapter(adapter);
        activityBinding.progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    private class GithubLoaderAsync extends AsyncTask {

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
                updateUI(response);
            }
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter {
        private Context context;
        private ArrayList<ItemsItem> dataSet;

        RecyclerAdapter(Context c) {
            context = c;
            dataSet = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.result_item, parent, false);

            // set the view's size, margins, paddings and layout parameters...
            ViewHolder vh = new ViewHolder(v);
            return vh;

        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            ((ViewHolder) holder).setObject(dataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void addItem(ItemsItem item) {
            dataSet.add(item);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ResultItemBinding binding;

            public ViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }

            public void setObject(ItemsItem item) {
                binding.setResultItem(item);
            }
        }
    }
}
