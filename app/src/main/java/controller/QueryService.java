package controller;

import java.util.List;

import outils.Inventor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QueryService {

    @FormUrlEncoded
    @GET("/maxrest/rest/os/SM_INV?")
    Call<List<Inventor>> query(
            @Query("_lid") String username,
            @Query("_lpwd") String password,
            @Query("_uw") String status


            );

}
