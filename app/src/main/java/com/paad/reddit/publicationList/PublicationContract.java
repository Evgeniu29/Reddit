package com.paad.reddit.publicationList;

import com.paad.reddit.model.Children;

import java.util.List;

public interface PublicationContract {

    interface View {

        void fillList(List<Children> childrenList);
        void showErrorMessage();
    }

    interface Presenter {

        void onLoadTop();


        List<Children> getFullList();
    }
}
