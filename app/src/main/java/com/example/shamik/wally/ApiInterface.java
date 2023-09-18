package com.example.shamik.wally;

import static com.example.shamik.wally.ApiUtilities.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    String Base_URL = "https://api.pexels.com/v1/";

    @Headers("Authorization: "+API)
    @GET("curated")
    Call<SearchModel> getImage(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @Headers("Authorization: "+API)
    @GET("search")
    Call<SearchModel> getSearchImage(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int per_page
    );

}
