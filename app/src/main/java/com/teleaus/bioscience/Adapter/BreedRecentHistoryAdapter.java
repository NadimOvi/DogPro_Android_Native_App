package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.teleaus.bioscience.DemoImageHomeAdapter;
import com.teleaus.bioscience.DemoImageHomeModel;
import com.teleaus.bioscience.Model.BreedHistoryData;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

public class BreedRecentHistoryAdapter extends RecyclerView.Adapter<BreedRecentHistoryAdapter.ViewHolder> {
    private ArrayList<BreedHistoryData> list;
    private Context context;
    private BreedRecentHistoryAdapter.RecyclerViewClickListener listener;

    public BreedRecentHistoryAdapter(ArrayList<BreedHistoryData> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BreedRecentHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*View view = LayoutInflater.from(context).inflate(R.layout.recent_history_breed_viewholder,viewGroup,false);
        return new BreedRecentHistoryAdapter.ViewHolder(view);*/

        return new BreedRecentHistoryAdapter.ViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.recent_history_breed_viewholder,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BreedRecentHistoryAdapter.ViewHolder holder, int position) {
        BreedHistoryData breedHistoryData = list.get(position);
       String imageURL = breedHistoryData.getImage_url();
       /*Glide.with()
                .load(imageURL)
                .into(holder.imagePoster);
        holder.textView.setText(breedHistoryData.getResponse().getData().get(0).getBreed());*/
        Picasso.get().load(imageURL).into(holder.imagePoster);
        /*holder.textView.setText(breedHistoryData.getResponse().getData().get(0).getBreed());*/
        /*holder.setData(breedHistoryData);*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagePoster;
       /* private TextView textView;*/
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.recentImagePoster);
           /* textView = itemView.findViewById(R.id.textView);*/
            itemView.setOnClickListener(this);
        }
        /*void setData(BreedHistoryData breedHistoryData){
           *//* imagePoster.set(breedHistoryData.getImage_path());*//*
            Picasso.get().load(breedHistoryData.getImage_url()).into(imagePoster);
            textView.setText(breedHistoryData.getResponse().getData().get(0).getBreed());
        }*/
        @Override
        public void onClick(View view) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }
}
