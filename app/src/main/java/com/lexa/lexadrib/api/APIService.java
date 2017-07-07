package com.lexa.lexadrib.api;

import com.lexa.lexadrib.data.Shot;
import com.lexa.lexadrib.data.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("/oauth/token")
    Call<Token> getToken(@Query("client_id") String client_id,
                         @Query("client_secret") String client_secret,
                         @Query("code") String code);

    @GET("shots")
    Call<List<Shot>>getShots(@Query("access_token") String accessToken,
                             @Query("page") String page,
                             @Query("per_page") String per_page);

    @GET("shots")
    Call<List<Shot>>getSortedShots(@Query("access_token") String accessToken,
                                   @Query("page") String page,
                                   @Query("per_page") String per_page,
                                   @Query("sort") String sort);

    @GET("shots/{id}")
    Call<Shot>getShot(@Path("id") Integer id,
                      @Query("access_token") String accessToken);

}
