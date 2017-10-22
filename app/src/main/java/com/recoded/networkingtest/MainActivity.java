package com.recoded.networkingtest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.recoded.networkingtest.databinding.ActivityMainBinding;

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

        (new GithubLoaderAsync(this)).execute();
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

}
