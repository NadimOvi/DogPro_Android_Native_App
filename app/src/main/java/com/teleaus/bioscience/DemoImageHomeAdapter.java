package com.teleaus.bioscience;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.teleaus.bioscience.Adapter.BreedRecentHistoryAdapter;
import com.teleaus.bioscience.Fragments.BreedRecentIndividualFragment;

import java.util.List;

public class DemoImageHomeAdapter extends RecyclerView.Adapter<DemoImageHomeAdapter.DemoImageViewHolder> {

    private final List<DemoImageHomeModel> modelList;
    private DemoImageHomeAdapter.RecyclerViewClickListener listener;
    private Context context;
    public DemoImageHomeAdapter(List<DemoImageHomeModel> modelList, Context context, RecyclerViewClickListener listenerDemo) {
        this.modelList = modelList;
        this.context = context;
        this.listener = listenerDemo;

    }

    @NonNull
    @Override
    public DemoImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoImageViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_list,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DemoImageHomeAdapter.DemoImageViewHolder holder, int position) {

        holder.setImage(modelList.get(position));

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class DemoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ConstraintLayout firstPage;
        private final ImageFilterView imagePoster;
        private final TextView textView;

        public DemoImageViewHolder(View view){
            super(view);
            firstPage = view.findViewById(R.id.firstPage);
            imagePoster = view.findViewById(R.id.imagePoster);
            textView = view.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        void setImage(DemoImageHomeModel demoImageHomeModel){
            imagePoster.setImageResource(demoImageHomeModel.poster);
            textView.setText(demoImageHomeModel.text);
        }
        @Override
        public void onClick(View view) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }

}
