package com.paad.reddit.retrofit;



import com.paad.reddit.model.TopResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/top/.json")
    Call<TopResponse> getTop(@Query("limit") int mount) ;

}
