package com.example.tz.Api;

import com.example.tz.Models.Hotel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHotel {
    @GET("35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    Call<Hotel> getHotel();


}
