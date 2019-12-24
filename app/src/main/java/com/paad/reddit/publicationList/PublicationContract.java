package com.paad.reddit.publicationList;

import com.paad.reddit.model.Children;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PublicationContract {

    interface View {

        void fillList();
        void showErrorMessage();
    }

    interface Presenter {

       List<Children> getTop() throws ExecutionException, InterruptedException;

    }
}
