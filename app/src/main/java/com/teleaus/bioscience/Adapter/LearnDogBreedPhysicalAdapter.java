package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.teleaus.bioscience.Model.getBreedHistoryListModelData;
import com.teleaus.bioscience.Model.getPhysicalAttibutesDataModel;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LearnDogBreedPhysicalAdapter extends RecyclerView.Adapter<LearnDogBreedPhysicalAdapter.ViewHolder> {

    private ArrayList<getPhysicalAttibutesDataModel> list;
    private Context context;
    private LearnDogBreedPhysicalAdapter.RecyclerViewClickListener adapterListener;

    public LearnDogBreedPhysicalAdapter(ArrayList<getPhysicalAttibutesDataModel> list, Context context, LearnDogBreedPhysicalAdapter.RecyclerViewClickListener adapterListener) {
        this.list = list;
        this.context = context;
        this.adapterListener = adapterListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.learn_dog_breed_physical_viewholder,viewGroup,false);
        return new LearnDogBreedPhysicalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        getPhysicalAttibutesDataModel dataList = list.get(position);
        String imageURL = dataList.getThumbnail();
        String breedName = dataList.getBreed();
        Picasso.get().load(imageURL).into(holder.profile_pic);
        holder.breedName.setText(breedName);


        StringBuilder lifeSpanbuilder = new StringBuilder();
        for (String details : dataList.getLife_span()) {
            lifeSpanbuilder.append(details + ".");
        }
        holder.lifeSpan.setText(lifeSpanbuilder.toString());

        StringBuilder ideal_weightBuilder = new StringBuilder();
        for (String details : dataList.getIdeal_weight()) {
            ideal_weightBuilder.append(details + ".");
        }
        holder.weightName.setText(ideal_weightBuilder.toString());

        StringBuilder ideal_heightBuilder = new StringBuilder();
        for (String details : dataList.getIdeal_height()) {
            ideal_heightBuilder.append(details + ".");
        }
        holder.heightName.setText(ideal_heightBuilder.toString());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profile_pic;
        TextView breedName,heightName,weightName,lifeSpan;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.breedName);
            heightName = itemView.findViewById(R.id.heightName);
            weightName = itemView.findViewById(R.id.weightName);
            lifeSpan = itemView.findViewById(R.id.lifeSpan);
            profile_pic = itemView.findViewById(R.id.profile_pic);
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
