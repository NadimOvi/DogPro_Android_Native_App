package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teleaus.bioscience.Model.Associated;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DiseaseListAdapter extends RecyclerView.Adapter<DiseaseListAdapter.ViewHolder> {

    private ArrayList<Associated> list;
    private Context context;
    private DiseaseListAdapter.RecyclerViewClickListener listener;

    public DiseaseListAdapter(ArrayList<Associated> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    @NotNull
    @Override
    public DiseaseListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.disease_view_holder,viewGroup,false);
        return new DiseaseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DiseaseListAdapter.ViewHolder holder, int position) {
        Associated dataList = list.get(position);
        String disease_name = dataList.getDisease_name();

        if (disease_name!=null){
          /*  holder.diseaseNameShow.setText(disease_name);*/
            holder.diseaseNameShow.setText(Html.fromHtml("<font color=#9e4c4e>  <u>" + disease_name + "</u>  </font>"));

        }else{
            Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView diseaseNameShow;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            diseaseNameShow = itemView.findViewById(R.id.diseaseNameShow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
