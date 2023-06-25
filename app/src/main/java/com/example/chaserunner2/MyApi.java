package com.example.chaserunner2;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface MyApi {
    @GET("/tip")
    Call<TipResponse> getTip();


//    @Headers("Content-Type: application/json")
    @POST("/characters")
    Call<CharacterResponse> createCharacter(@Body CharacterRequest request);

}
