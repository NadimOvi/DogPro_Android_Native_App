package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDogBreedListAdapter extends RecyclerView.Adapter<AllDogBreedListAdapter.ViewHolder> /*implements Filterable*/ {
    private ArrayList<DataList> list;
    /*private ArrayList<DataList> listFilter = new ArrayList<>();*/
    private Context context;
    private AllDogBreedListAdapter.RecyclerViewClickListener adapterListener;

    public AllDogBreedListAdapter(ArrayList<DataList> list, Context context, RecyclerViewClickListener adapterListener) {
        this.list = list;
        /*this.listFilter = list;*/
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @NonNull
    @Override
    public AllDogBreedListAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_breed_list_viewholder,viewGroup,false);
        return new AllDogBreedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AllDogBreedListAdapter.ViewHolder holder, int position) {
        DataList dataList = list.get(position);
        String imageURL = dataList.getThumbnail();
        String breedName = dataList.getName();
        Glide.with(context)
                .load(imageURL)
                .into(holder.dogImage);
        holder.breedName.setText(breedName);

    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    /*@Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0){
                    filterResults.values = listFilter;
                    filterResults.count = listFilter.size();
                }else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<DataList> userDataList = new ArrayList<>();
                    for (DataList dataList: listFilter){
                        if (dataList.getName().toLowerCase().contains(searchStr)){
                            userDataList.add(dataList);
                        }
                    }
                    filterResults.values = userDataList;
                    filterResults.count = userDataList.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                list = (ArrayList<DataList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }*/

    /*private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DataList> filterNewList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterNewList.addAll(newList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataList dataList : newList){
                    if (dataList.getName().toLowerCase().contains(filterPattern)){
                        filterNewList.add(dataList);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterNewList;
            results.count = filterNewList.size();

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView dogImage;
        TextView breedName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.breedName);
            dogImage = itemView.findViewById(R.id.dogImage);
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
