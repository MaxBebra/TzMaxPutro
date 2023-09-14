package com.example.tz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tz.Api.ReservatApi;
import com.example.tz.Models.ResevationModel;
import com.example.tz.databinding.ActivityRezservationNumberBinding;
import com.example.tz.databinding.AddPersonLayoutBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class rezservationNumberActivity extends AppCompatActivity {
 ActivityRezservationNumberBinding binding;
    private boolean isInfoVisible = false;
    private boolean isInfoVisible2 = false;

    private int touristCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRezservationNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        setListeners();



    }
    private void setListeners(){

        EditText[] editTexts = {
                findViewById(R.id.editTextName),
                findViewById(R.id.editTextSecondName),
                findViewById(R.id.editTextBirthdayDate),
                findViewById(R.id.editTextCountry),
                findViewById(R.id.editTextPassport),
                findViewById(R.id.editTextPassportDate)
        };

        LinearLayout[] linearLayouts = {
                binding.nameHolder,
                binding.secondNameHolder,
                binding.birthdayDateHolder,
                binding.CountryHolder,
                binding.PassportNumberHolder,
                binding.PassportNuberDateHolder

        };

        binding.addPerson.setOnClickListener(view -> {
            if (touristCounter < 3) {
                touristCounter++;

                View addPersonLayout = getLayoutInflater().inflate(R.layout.add_person_layout, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 8, 0, 8);
                addPersonLayout.setLayoutParams(layoutParams);


                TextView touristCountTextView = addPersonLayout.findViewById(R.id.touristCount);
                touristCountTextView.setText(getTouristCountText(touristCounter));

                binding.opa.addView(addPersonLayout);

                AddPersonLayoutBinding addPersonBinding = AddPersonLayoutBinding.bind(addPersonLayout);
                ImageView showInfo = addPersonBinding.showInfo;
                LinearLayout infoLayout = addPersonBinding.infoLayout;
                CardView cardView = addPersonBinding.expandCard;

                showInfo.setOnClickListener(view1 -> {
                    isInfoVisible = !isInfoVisible;
                    if (!isInfoVisible) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        infoLayout.setVisibility(View.GONE);
                        showInfo.setImageResource(R.drawable.arrow_down);

                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        infoLayout.setVisibility(View.VISIBLE);
                        showInfo.setImageResource(R.drawable.arrow_up);

                    }
                });
            } else {
                Toast.makeText(this, "Больше нельзя добавить туристов", Toast.LENGTH_SHORT).show();
            }
        });

        binding.showInfo.setOnClickListener(view -> {
            isInfoVisible2 = !isInfoVisible2;
            if (!isInfoVisible2) {
                TransitionManager.beginDelayedTransition(binding.expandCard, new AutoTransition());
                binding.infoLayout.setVisibility(View.GONE);
                binding.showInfo.setImageResource(R.drawable.arrow_down);
                isInfoVisible2 = false;
            } else {
                TransitionManager.beginDelayedTransition(binding.expandCard, new AutoTransition());
                binding.infoLayout.setVisibility(View.VISIBLE);
                binding.showInfo.setImageResource(R.drawable.arrow_up);
                isInfoVisible2 = true;
            }
        });

        binding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        binding.editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // Пользователь потерял фокус ввода, проверьте корректность адреса
                    String email = binding.editTextEmail.getText().toString();
                    if (!isValidEmail(email)) {
                        // Адрес некорректен, измените фон на красный с непрозрачностью 15%
                        binding.linearLayoutEdittextHolder.setBackgroundResource(R.drawable.bg_for_edit_text_error);

                    }else {
                        binding.linearLayoutEdittextHolder.setBackgroundResource(R.drawable.bg_for_edit_text);

                    }
                }
            }
        });

        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();
                if (isValidEmail(email)) {

                    binding.linearLayoutEdittextHolder.setBackgroundResource(R.drawable.bg_for_edit_text);
                }
            }
        });

        binding.pay.setOnClickListener(view -> {
            boolean allFieldsFilled = true;

            for (int i = 0; i < editTexts.length; i++) {
                EditText editText = editTexts[i];
                LinearLayout layout = linearLayouts[i];

                if (TextUtils.isEmpty(editText.getText().toString())) {

                    layout.setBackgroundResource(R.drawable.bg_for_edit_text_error);
                    allFieldsFilled = false;
                } else {

                    layout.setBackgroundResource(R.drawable.bg_for_edit_text);
                }
            }

            if (allFieldsFilled) {

                Intent intent = new Intent(rezservationNumberActivity.this,PaySuccsess.class);
                startActivity(intent);
            } else {

                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });





    }
    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReservatApi reservatApi = retrofit.create(ReservatApi.class);

        Call<ResevationModel> call = reservatApi.getReservModel();
        call.enqueue(new Callback<ResevationModel>() {
            @Override
            public void onResponse(Call<ResevationModel> call, Response<ResevationModel> response) {
                ResevationModel resevationModel = response.body();

                String hotelName = resevationModel.getHotel_name();
                String horating = resevationModel.getHorating();
                String raitingName = resevationModel.getRating_name();
                String hotelAddress = resevationModel.getHotel_adress();
                String departure = resevationModel.getDeparture();
                String arrival_country = resevationModel.getArrival_country();
                String tour_date_start = resevationModel.getTour_date_start();
                String tour_date_stop = resevationModel.getTour_date_stop();
                String number_of_nights = resevationModel.getNumber_of_nights();
                String room = resevationModel.getRoom();
                String nutrition  = resevationModel.getNutrition();

                String tourPrice = resevationModel.getTour_price();
                String fuel_charge = resevationModel.getFuel_charge();
                String service_charge = resevationModel.getService_charge();

                //ценники
                int tourPriceForm = Integer.parseInt(tourPrice);
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                String tourPriceFormated = numberFormat.format(tourPriceForm) + " ₽";

                int fuelChargeForm = Integer.parseInt(fuel_charge);
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.getDefault());
                String fuelChargeFormated = numberFormat2.format(fuelChargeForm) + " ₽";

                int ServiceChargeForm = Integer.parseInt(service_charge);
                NumberFormat numberFormat3 = NumberFormat.getNumberInstance(Locale.getDefault());
                String ServiceChargeFormated = numberFormat3.format(ServiceChargeForm) + " ₽";


                double tourPriceValue = Double.parseDouble(tourPrice);
                double fuelChargeValue = Double.parseDouble(fuel_charge);
                double serviceChargeValue = Double.parseDouble(service_charge);


                double totalAmount = tourPriceValue + fuelChargeValue + serviceChargeValue;

                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedTotalAmount = decimalFormat.format(totalAmount);


                binding.pay.setText("Оплатить " +formattedTotalAmount + " ₽");

                binding.hotelName.setText(hotelName);
                binding.raiting.setText(horating);
                binding.raitingName.setText(raitingName);
                binding.hotelAddress.setText(hotelAddress);
                binding.cityOut.setText(departure);
                binding.CountryCity.setText(arrival_country);
                binding.date.setText(tour_date_start + " - " +tour_date_stop);
                binding.nightCount.setText(number_of_nights+" ночей");
                binding.hotelName2.setText(hotelName);
                binding.numberType.setText(room);
                binding.nutrition.setText(nutrition);


                binding.tourPrice.setText(tourPriceFormated);
                binding.fuelCharge.setText(fuelChargeFormated);
                binding.serviceCharge.setText(ServiceChargeFormated);
                binding.fullSum.setText(formattedTotalAmount+ " ₽");

            }

            @Override
            public void onFailure(Call<ResevationModel> call, Throwable t) {
                    Toast.makeText(rezservationNumberActivity.this,"Не удалось загрузить данные"+" "+t,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private String getTouristCountText(int count) {
        switch (count) {
            case 1:
                return "Второй турист";
            case 2:
                return "Третий турист";
            case 3:
                return "Четвертый турист";
            default:
                return "";
        }
    }

}