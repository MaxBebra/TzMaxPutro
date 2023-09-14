package com.example.tz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tz.R;

import java.util.List;

public class PeculiarityAdapter extends RecyclerView.Adapter<PeculiarityAdapter.ViewHolder> {

    private Context context;
    private List<String> peculiaritiesList;

    public PeculiarityAdapter(Context context, List<String> peculiaritiesList) {
        this.context = context;
        this.peculiaritiesList = peculiaritiesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.peculiarities_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String peculiarities = peculiaritiesList.get(position);
        holder.peculiarityTextView.setText(peculiarities);
    }

    @Override
    public int getItemCount() {
        return peculiaritiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView peculiarityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            peculiarityTextView = itemView.findViewById(R.id.peculiarityTextView);
        }
    }
}
