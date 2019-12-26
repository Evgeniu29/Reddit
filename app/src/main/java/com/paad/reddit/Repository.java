package com.paad.reddit;


import android.content.Context;
import android.util.Log;
import com.paad.reddit.model.TopResponse;
import com.paad.reddit.model.TopResponseData;
import com.paad.reddit.retrofit.ApiClient;
import com.paad.reddit.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private ApiService service;

    private Context  context;


    private TopResponseData interactorTopResponseData;

    public Repository(Context  context) {

        this.context = context;

        service = ApiClient.getClient().create(ApiService.class);

    }

    public void getTop(final ResponseCallback cllbk){

        this.service.getTop(50).enqueue(new Callback<TopResponse>() {
            @Override
            public void onResponse(Call<TopResponse> call, Response<TopResponse> response) {
                Log.v("response", response.body().toString());
                if(cllbk!=null)
                    cllbk.onDataReady(response.body());
            }

            @Override
            public void onFailure(Call<TopResponse> call, Throwable t) {
                Log.e("failure","=>" + t.getMessage());
                if(cllbk!=null)
                    cllbk.onError(t);
            }
        });
    }

    public TopResponseData getInteractorTopResponseData() {
        if(interactorTopResponseData != null) {
            return interactorTopResponseData;
        }
        return null;
    }

    public interface ResponseCallback{
        void onDataReady(TopResponse response);
        void onError(Throwable t);
    }




}
