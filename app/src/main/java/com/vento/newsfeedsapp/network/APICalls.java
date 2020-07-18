package com.vento.newsfeedsapp.network;

import com.vento.newsfeedsapp.ui.model.ArticlesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;



public interface APICalls {

     @GET("articles?")
     Single<ArticlesResponse> articlesAssociatedPress(@Query("source") String source
                                                     ,@Query("apiKey") String apiKey);

     @GET("articles?")
     Single<ArticlesResponse> articlesTheNextWeb(@Query("source") String source
                                                ,@Query("apiKey") String apiKey);




    
}
