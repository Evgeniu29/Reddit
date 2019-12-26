package com.paad.reddit.publicationList;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paad.reddit.R;
import com.paad.reddit.TransferBetweenFragments;
import com.paad.reddit.model.Children;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class PublicationFragment extends Fragment implements PublicationContract.View {

    private PublicationContract.Presenter presenter;
    TransferBetweenFragments transferBetweenFragments;
    private RecyclerView recyclerView;
    private Context context;
    private  PublicationAdapter adapter;
    private List<Children> childrenList;
    boolean isLoading=false;

    public void onAttach(Context context) {
        super.onAttach(context);
       this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publication, container, false);

    }

    public static PublicationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PublicationFragment fragment = new PublicationFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        presenter = new PublicationPresenter(this, (Application)this.context.getApplicationContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.publication_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        presenter.onLoadTop();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == childrenList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }



    private boolean islastItemDisplaying(RecyclerView recyclerView){
        //check if the adapter item count is greater than 0
        if(recyclerView.getAdapter().getItemCount() != 0){
            //get the last visible item on screen using the layoutmanager
            int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            //apply some logic here.
            if(lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }

        return  false;
    }


    private void loadMore() {
        childrenList.add(null);
        adapter.notifyItemInserted( childrenList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                childrenList.remove( childrenList.size() - 1);
                int scrollPosition =  childrenList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int insertIndex=scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {

                    Children children = presenter.getFullList().get(currentSize);

                    childrenList.add(children);


                    currentSize++;
                }
                

                fillList(childrenList);

                isLoading = false;
            }
        }, 2000);


    }


    @Override
    public void fillList(List<Children> childrenList) {
        this.childrenList = childrenList;

        adapter = new PublicationAdapter(getContext(), this.childrenList, publicationID -> {
            transferBetweenFragments.goFromPublicationToPublication(publicationID);
        });

        recyclerView.setAdapter(adapter);
    }



    @Override
    public void showErrorMessage() {

        Toast.makeText(this.getContext(),"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

    }




}
