package com.example.kurirjualanpraktis.api;

import com.example.kurirjualanpraktis.model.login.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> login(@Field("email") String email,
                              @Field("password") String password);

}
