package com.paad.reddit.publicationList;

import android.content.Context;
import android.os.Bundle;
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
import com.paad.reddit.Repository;
import com.paad.reddit.TransferBetweenFragments;
import com.paad.reddit.model.Children;
import com.paad.reddit.model.TopResponse;

import java.util.List;


public class PublicationFragment extends Fragment implements PublicationContract.View {

    private PublicationContract.Presenter presenter;
    TransferBetweenFragments transferBetweenFragments;
    private RecyclerView recyclerView;
    private Context context;
    private  PublicationAdapter adapter;
    private List<Children> childrenList;

    private int page = 1, limit = 15;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private int visibleThreshold = 10;


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

        recyclerView = (RecyclerView) view.findViewById(R.id.publication_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        Repository repository = new Repository(context);

        // Get top
        repository.getTop(new Repository.ResponseCallback() {
            @Override
            public void onDataReady(TopResponse response) {
                if (response != null && response.getData() != null)
                    childrenList = response.getData().getChildrenList();
                fillList();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("error", " =>" + t.getMessage());
            }
        });
    }


        public void fillList () {

            adapter = new PublicationAdapter(getContext(), childrenList, publicationID -> {
                transferBetweenFragments.goFromPublicationToPublication(publicationID);


            });

            recyclerView.setAdapter(adapter);
        }



    @Override
    public void showErrorMessage() {

        Toast.makeText(this.getContext(),"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

    }

}
