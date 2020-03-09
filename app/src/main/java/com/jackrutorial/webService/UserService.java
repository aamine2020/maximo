package com.jackrutorial.webService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("/maxrest/rest/login?")
    Call<Void> login(
            @Field("_lid") String username,
            @Field("_lpwd") String password
            );

// sof

}
