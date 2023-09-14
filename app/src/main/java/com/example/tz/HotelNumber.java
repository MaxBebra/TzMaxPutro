package com.example.tz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tz.Adapters.NumberAdapter;
import com.example.tz.Api.NumberApi;
import com.example.tz.Models.NumberModel;
import com.example.tz.Responses.HotelResponse;
import com.example.tz.databinding.ActivityHotelNumberBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelNumber extends AppCompatActivity {
ActivityHotelNumberBinding binding;
NumberAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loaddata();
        setListeners();


        //получаем название отеля с Api
        Intent intent = getIntent();
        if (intent != null) {
            String hotelName = intent.getStringExtra("hotelName");
            if (hotelName != null) {
                binding.toolbar.setTitle(hotelName);
            }
        }


    }


    private void setListeners(){
        binding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void loaddata(){

        // отображаем номра в recycler
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NumberApi api = retrofit.create(NumberApi.class);
        api.getNumberModel().enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if (response.isSuccessful()) {
                    HotelResponse hotelResponse = response.body();
                    if (hotelResponse != null) {
                        List<NumberModel> numberModels = hotelResponse.getRooms();
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(HotelNumber.this));
                        adapter = new NumberAdapter(HotelNumber.this, numberModels);
                        binding.recyclerView.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                Toast.makeText(HotelNumber.this, "Ошибка загрузки данных: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }


}