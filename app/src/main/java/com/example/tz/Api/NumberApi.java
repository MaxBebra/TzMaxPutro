package com.example.tz.Api;
import com.example.tz.Responses.HotelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
public interface NumberApi {
    @GET("f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    Call<HotelResponse> getNumberModel();
}
