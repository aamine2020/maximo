package controller;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {



    @FormUrlEncoded
    @POST("/maxrest/rest/login?")
    Call<Void> login(
            @Field("_lid") String username,
            @Field("_lpwd") String password
            );


//test ayari

}
