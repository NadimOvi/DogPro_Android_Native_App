package com.teleaus.bioscience.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.Model.getBreedHistoryListModelData;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class LearnDogHistoryAdapter extends RecyclerView.Adapter<LearnDogHistoryAdapter.ViewHolder> {

    private ArrayList<getBreedHistoryListModelData> list;
    private Context context;
    private LearnDogHistoryAdapter.RecyclerViewClickListener adapterListener;

    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    static Pattern htmlValidator = TextUtils.isEmpty(HTML_TAG_PATTERN) ? null:Pattern.compile(HTML_TAG_PATTERN);

    public LearnDogHistoryAdapter(ArrayList<getBreedHistoryListModelData> list, Context context, RecyclerViewClickListener adapterListener) {
        this.list = list;
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @NonNull
    @NotNull
    @Override
    public LearnDogHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.learn_dog_breed_viewholder,viewGroup,false);
        return new LearnDogHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LearnDogHistoryAdapter.ViewHolder holder, int position) {
        getBreedHistoryListModelData dataList = list.get(position);
        String imageURL = dataList.getThumbnail();
        String breedName = dataList.getBreed_name();
        String breedDescription = dataList.getHistory();

        /*Glide.with(context)
                .load(imageURL)
                .into(holder.dogImage);*/

        Picasso.get().load(imageURL).into(holder.profile_pic);

        holder.breedName.setText(breedName);
        holder.historyShow.setText(breedDescription);


        if (validateHtml(breedDescription)){
            holder.historyShow.setText(Html.fromHtml(breedDescription));
        }else{
            holder.historyShow.setText(breedDescription);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView profile_pic;
        TextView breedName,historyShow;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.breedName);
            historyShow = itemView.findViewById(R.id.historyShow);
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

    public static boolean validateHtml(final String text){
        if(htmlValidator !=null)
            return htmlValidator.matcher(text).find();
        return false;
    }
}
