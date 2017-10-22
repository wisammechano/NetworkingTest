package com.recoded.networkingtest;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<ItemsItem> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<ItemsItem> getItems() {
        return items;
    }

    public void setItems(List<ItemsItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "total_count = '" + totalCount + '\'' +
                        ",incomplete_results = '" + incompleteResults + '\'' +
                        ",items = '" + items + '\'' +
                        "}";
    }
}