package com.example.tz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tz.Models.NumberModel;
import com.example.tz.R;
import com.example.tz.rezservationNumberActivity;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {
    private Context context;
    private List<NumberModel> numbers;
    private List<String> peculiarities;

    List<SlideModel> imageList = new ArrayList<>();
    private NumberModel numberModel;

    public NumberAdapter(Context context, List<NumberModel> numbers) {
        this.context = context;
        this.numbers = numbers;
    }
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_item, parent, false);
        return new NumberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.ViewHolder holder, int position) {
        NumberModel numberModel = numbers.get(position);

        //вставка данных
        holder.Name.setText(numberModel.getName());
        int price = Integer.parseInt(numberModel.getPrice());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = numberFormat.format(price) + " ₽";
        holder.Price.setText(formattedPrice);
        holder.price_per.setText(numberModel.getPrice_per());
        //вставка данных

        // отображение особенностей в recyclerView
        peculiarities = numberModel.getPeculiarities();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        holder.recyclerView.setLayoutManager(layoutManager);
        PeculiarityAdapter adapter = new PeculiarityAdapter(context, peculiarities);
        holder.recyclerView.setAdapter(adapter);

        // отображение картинок номеров в slider

        imageList.clear();

        List<String> imageUrls = numberModel.getImage_urls();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                imageList.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
            }
        }
        holder.imageSlider.setImageList(imageList);


        holder.button.setOnClickListener(view -> {
            Intent intent = new Intent(context, rezservationNumberActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Price,price_per;
        ImageSlider imageSlider;
        RecyclerView recyclerView;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
             Name = itemView.findViewById(R.id.NumberName);
            Price = itemView.findViewById(R.id.Price);
            price_per = itemView.findViewById(R.id.price_per);
            imageSlider = itemView.findViewById(R.id.imageSlider);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            button = itemView.findViewById(R.id.selectnumber);
        }
    }
}
