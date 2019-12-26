package com.paad.reddit.publicationList;


import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

public class AddItemsTask extends AsyncTask<Void, Void, Void> {

    private OnAddItemListener addItemToAdapter;

    public AddItemsTask(OnAddItemListener  addItemToAdapter) {
        this.addItemToAdapter =  addItemToAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        addItemToAdapter.onStartTask();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        addItemToAdapter.onFinishTask();
    }

    public interface OnAddItemListener {
        void onStartTask();
        void onFinishTask();
    }
}