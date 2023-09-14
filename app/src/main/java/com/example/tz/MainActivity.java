package com.example.tz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tz.Adapters.PeculiarityAdapter;
import com.example.tz.Api.ApiHotel;
import com.example.tz.Models.AboutTheHotel;
import com.example.tz.Models.Hotel;
import com.example.tz.databinding.ActivityMainBinding;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    List<SlideModel> imageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getHotelInfo();
        setListeners();





    }
    private void getHotelInfo(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        ApiHotel apiHotel = retrofit.create(ApiHotel.class);
        Call<Hotel> call = apiHotel.getHotel();
        call.enqueue(new Callback<Hotel>() {

            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                if (response.isSuccessful()) {
                    Hotel hotel = response.body();
                    if (hotel != null) {

                        // получаем данные с api
                        String hotelName = hotel.getName();
                        String hotelAdress = hotel.getAdress();
                        String hotelPriceForIt = hotel.getPrice_for_it();
                        String minPrice = hotel.getMinimal_price();
                        String rating = hotel.getRating();
                        String ratingName = hotel.getRating_name();
                        int price = Integer.parseInt(minPrice);
                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                        String formattedPrice = "от " + numberFormat.format(price) + " ₽";

                        //вставляем информацию
                        binding.priceForIt.setText(hotelPriceForIt);
                        binding.HotelAddress.setText(hotelAdress);
                        binding.HotelName.setText(hotelName);
                        binding.MinimalPrice.setText(formattedPrice);
                        binding.raiting.setText(rating);
                        binding.raitingName.setText(ratingName);
                        AboutTheHotel aboutTheHotel = hotel.getAboutTheHotel();
                        String hotelDesc = aboutTheHotel.getDescription();
                        binding.descriptionInfo.setText(hotelDesc);

                        // очищяем list изображений и вставляем url в slider
                        imageList.clear();
                        List<String> imageUrls = hotel.getImage_urls();
                        if (imageUrls != null && !imageUrls.isEmpty()) {
                            for (String imageUrl : imageUrls) {
                                imageList.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
                            }
                        }
                        binding.imageSlider.setImageList(imageList);

                        // получаем особенности отеля из api и вставляем их в FlexBBox
                        if (aboutTheHotel != null) {
                            List<String> peculiaritiesList = aboutTheHotel.getPeculiarities();
                            if (peculiaritiesList != null && !peculiaritiesList.isEmpty()) {
                                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(MainActivity.this);
                                layoutManager.setFlexDirection(FlexDirection.ROW);
                                binding.recyclerView.setLayoutManager(layoutManager);
                                PeculiarityAdapter adapter = new PeculiarityAdapter(MainActivity.this, peculiaritiesList);
                                binding.recyclerView.setAdapter(adapter);
                            }
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Ответ не содержит данных отеля", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Не удалось получить данные отеля", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {

            }
        });

    }
    private void setListeners(){

        // переходим в активность и передаем название отеля
        binding.selectnumber.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,HotelNumber.class);
            String hotelName = binding.HotelName.getText().toString();
            intent.putExtra("hotelName", hotelName);
            startActivity(intent);
        });
    }




}