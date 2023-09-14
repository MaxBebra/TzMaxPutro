package com.example.tz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tz.databinding.ActivityPaySuccsessBinding;

import java.util.Random;

public class PaySuccsess extends AppCompatActivity {
ActivityPaySuccsessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaySuccsessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        generateOderNumber();
        setListeners();






    }
    private void setListeners(){

        binding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        binding.nice.setOnClickListener(view -> {
            Intent intent = new Intent(PaySuccsess.this,MainActivity.class);
            startActivity(intent);
        });

    }

    private void generateOderNumber(){

        //создаем рандомное значение и вставляем его в text
        Random random = new Random();
        int orderNumberInt = random.nextInt(900000) + 100000;

        String orderNumber = String.valueOf(orderNumberInt);

        binding.orderInfo.setText("Подтверждение заказа " +"№"+orderNumber+ " может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление.");

    }


}