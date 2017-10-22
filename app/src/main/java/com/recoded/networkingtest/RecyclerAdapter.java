package com.recoded.networkingtest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoded.networkingtest.databinding.ResultItemBinding;

import java.util.ArrayList;

/**
 * Created by wisam on Oct 22 17.
 */
class RecyclerAdapter extends RecyclerView.Adapter {
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
        return new ViewHolder(v);
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
