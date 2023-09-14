package com.example.tz.Api;

import com.example.tz.Models.ResevationModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReservatApi {

    @GET("e8868481-743f-4eb2-a0d7-2bc4012275c8")
    Call<ResevationModel> getReservModel();
}
