package com.paad.reddit.publicationList;

import com.paad.reddit.model.Children;

import java.util.ArrayList;

public interface PublicationContract {

    interface View {

        void fillList(ArrayList<Children> childrenList);
        void showErrorMessage();
    }

    interface Presenter {

        void onLoadTop();



    }
}
