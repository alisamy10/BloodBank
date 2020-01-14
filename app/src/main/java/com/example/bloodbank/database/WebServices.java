package com.example.bloodbank.database;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface WebServices {

    @FormUrlEncoded
    @POST("register.php")
    Call  <String>createUser(@Field("name")String name , @Field("city")String city,@Field("blood_group") String bloodGroup
    ,@Field("password")String Password,@Field("number")String number);

    @FormUrlEncoded
    @POST("login.php")
    Call  <String>loginUser(@Field("password")String Password,@Field("number")String number);
}
