package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.teleaus.bioscience.LocalDatabase.LocalHistoryResponseData;
import com.teleaus.bioscience.Model.BreedHistoryResponseData;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentDogBreedResultAdapter extends RecyclerView.Adapter<RecentDogBreedResultAdapter.ViewHolder> {

    private ArrayList<LocalHistoryResponseData> list;
    private Context context;
    private RecentDogBreedResultAdapter.RecyclerViewClickListener adapterListener;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    Toolbar toolbarId;
    String imageURL;
    PhotoView recyclerViewImage;

    public RecentDogBreedResultAdapter(ArrayList<LocalHistoryResponseData> list, Context context, RecyclerViewClickListener adapterListener) {
        this.list = list;
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @NonNull

    @Override
    public RecentDogBreedResultAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_dog_view_holder,viewGroup,false);
        return new RecentDogBreedResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecentDogBreedResultAdapter.ViewHolder holder, int position) {
        LocalHistoryResponseData dataClass = list.get(position);
        imageURL = dataClass.getThumbnail();
        String BreedName = dataClass.getBreed();
        String BreedPercentage = dataClass.getPercentage();

        Glide.with(context)
                .load(imageURL)
                .into(holder.profile_pic);
        holder.breedName.setText(BreedName);
        holder.percentageShow.setText(BreedPercentage+"%"+" match");

        holder.profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.image_view_controller,null);

                recyclerViewImage = dialogView.findViewById(R.id.recyclerViewImage);
                toolbarId = dialogView.findViewById(R.id.toolbarId);

                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                dialog = dialogBuilder.create();
                dialog.show();

                Glide.with(view.getRootView().getContext())
                        .load(dataClass.getThumbnail())
                        .into(recyclerViewImage);

                /*toolbarId.setNavigationIcon(R.drawable.ic_backbutton);

                toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.getRootView().getContext().onBackPressed();
                    }
                });*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView breedName, percentageShow;
        private CircleImageView profile_pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.breedName);
            percentageShow = itemView.findViewById(R.id.percentageShow);
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
