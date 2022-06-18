package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teleaus.bioscience.Model.BreedHistoryResponseData;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.Model.FilterBreedData;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FilterSearchAdapter extends RecyclerView.Adapter<FilterSearchAdapter.ViewHolder> {

    private ArrayList<FilterBreedData> list;
    private Context context;
    private FilterSearchAdapter.RecyclerViewClickListener adapterListener;
    public FilterSearchAdapter(ArrayList<FilterBreedData> list, Context context, FilterSearchAdapter.RecyclerViewClickListener adapterListener) {
        this.list = list;
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @NonNull
    @NotNull
    @Override
    public FilterSearchAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_breed_list_viewholder,viewGroup,false);
        return new FilterSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterSearchAdapter.ViewHolder holder, int position) {
        FilterBreedData filterBreedData = list.get(position);
        String imageURL = filterBreedData.getThumbnail();
        String breedName = filterBreedData.getBreed();
        String breedDescription = filterBreedData.getBrief_history();
        Glide.with(context)
                .load(imageURL)
                .into(holder.dogImage);
        holder.breedName.setText(breedName);
        
        holder.descriptionShow.setText(breedDescription);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView dogImage;
        TextView breedName,descriptionShow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.breedName);
            dogImage = itemView.findViewById(R.id.dogImage);
            descriptionShow = itemView.findViewById(R.id.descriptionShow);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            adapterListener.onClick(itemView,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
