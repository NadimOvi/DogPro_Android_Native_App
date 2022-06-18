package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.bioscience.Model.Associated;
import com.teleaus.bioscience.Model.Associated_diseases;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AssociatedDiseasesAdapter extends RecyclerView.Adapter<AssociatedDiseasesAdapter.ViewHolder>{
    private ArrayList<Associated_diseases> list;
    private Context context;

    public AssociatedDiseasesAdapter(ArrayList<Associated_diseases> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NotNull
    @Override
    public AssociatedDiseasesAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.associated_view_holder,viewGroup,false);
        return new AssociatedDiseasesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AssociatedDiseasesAdapter.ViewHolder holder, int position) {
        Associated_diseases dataList = list.get(position);
        if (dataList!=null){
           holder.diseaseNameAgain.setText(dataList.getName());
           holder.otherNameShow.setText(dataList.getOther_name());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView diseaseNameAgain,otherNameShow;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            diseaseNameAgain = itemView.findViewById(R.id.diseaseNameAgain);
            otherNameShow = itemView.findViewById(R.id.otherNameShow);
        }
    }
}
