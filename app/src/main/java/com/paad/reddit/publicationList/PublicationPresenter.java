package com.paad.reddit.publicationList;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.paad.reddit.Repository;
import com.paad.reddit.model.Children;
import com.paad.reddit.model.TopResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PublicationPresenter  implements PublicationContract.Presenter {

    private Repository repository;
    private Application application;

    private PublicationContract.View view;


    public PublicationPresenter(PublicationContract.View view, Application application) {
        this.view = view;
        this.application = application;
        repository = new Repository(application);

    }

    public void onLoadTop() {


        repository.getTop(new Repository.ResponseCallback() {
            @Override
            public void onDataReady(TopResponse response) {


                if (response != null && response.getData() != null)



                view.fillList(response.getData().getChildrenList());


            }

            @Override
            public void onError(Throwable t) {
                Log.e("error", " =>" + t.getMessage());
            }
        });

    }




}
