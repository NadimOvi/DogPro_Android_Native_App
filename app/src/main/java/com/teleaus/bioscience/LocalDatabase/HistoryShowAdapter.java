package com.teleaus.bioscience.LocalDatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.basusingh.beautifulprogressdialog.BeautifulProgressDialog;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Adapter.BreedRecentHistoryAdapter;
import com.teleaus.bioscience.Adapter.DogBreedResultAdapter;
import com.teleaus.bioscience.Fragments.BreedHistoryIndividualFragment;
import com.teleaus.bioscience.MainActivity;
import com.teleaus.bioscience.Model.BreedHistoryData;
import com.teleaus.bioscience.Model.BreedHistoryResponseData;
import com.teleaus.bioscience.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryShowAdapter extends RecyclerView.Adapter<HistoryShowAdapter.ViewHolder> {
    private List<HistoryDemoModel> lists;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private ArrayList<HistoryShowData> dataClassList;
    BeautifulProgressDialog progressDialog;

    int id_pass;
    String image_url;
    ArrayList<BreedHistoryResponseData> breedHistoryResponseData;

    public HistoryShowAdapter(ArrayList<HistoryDemoModel> list, Context context, RecyclerView recyclerView/*, ArrayList<HistoryShowData> dataClassList*/) {
        lists = list;
        mContext = context;
        mRecyclerV = recyclerView;
        /*dataClassList = dataClassList;*/
    }

    @NotNull
    @Override
    public HistoryShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_show_viewholder,viewGroup,false);
        return new HistoryShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryShowAdapter.ViewHolder holder, int position) {

        HistoryDemoModel historyDemoModel = lists.get(position);
      /*  progressDialog.show();*/

        String id =historyDemoModel.getIds();

        getHistoryList(id,holder,position);

        /*progressDialog.dismiss();*/

        holder.returnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.showTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* progressDialog.show();*/
                String BASE_URL = "https://genofax.com/api/breed/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                /*Service service = Client.retrofit.create(Service.class);*/

                Service service = retrofit.create(Service.class);
                Call<HistoryShowMainModel> call = service.getHistoryList(id);
                call.enqueue(new Callback<HistoryShowMainModel>() {
                    @Override
                    public void onResponse(Call<HistoryShowMainModel> call, Response<HistoryShowMainModel> response) {
                        /*progressDialog.dismiss();*/
                        int id_pass;
                        String image_url;
                        ArrayList<HistoryShowData> dataClassList;
                        ArrayList<LocalHistoryResponseData> localHistoryResponseData;

                        if (response.isSuccessful()){
                            dataClassList = response.body().getData();
                            id_pass = dataClassList.get(0).getId();
                            image_url = dataClassList.get(0).getImage_url();
                            localHistoryResponseData= dataClassList.get(0).getResponse().getData();


                            BreedHistoryIndividualFragment breedHistoryIndividualFragment = new BreedHistoryIndividualFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id_pass);
                            bundle.putString("image_url",image_url);
                            bundle.putString("classification",dataClassList.get(0).getResponse().getClassification_summary());
                            bundle.putParcelableArrayList("list",localHistoryResponseData);

                            breedHistoryIndividualFragment.setArguments(bundle);
                             /*getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, breedHistoryIndividualFragment)
                                .addToBackStack(null).commit();*/
                            FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.addToBackStack(null);
                            ft.replace(R.id.fragment_container, breedHistoryIndividualFragment);
                            ft.commit();

                        }else{
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryShowMainModel> call, Throwable t) {
                        Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.menu_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.menu_txt);
                //inflating menu from xml resource
                popup.inflate(R.menu.recycler_option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_remove:
                                //handle menu1 click
                                Toast.makeText(mContext, "Data Remove", Toast.LENGTH_SHORT).show();
                                MyDatabaseHelper dbHelper = new MyDatabaseHelper(mContext);
                                dbHelper.deletePersonRecord(historyDemoModel.getId(), mContext);

                                lists.remove(position);
                                mRecyclerV.removeViewAt(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, lists.size());
                                notifyDataSetChanged();
                                return true;

                            default:
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        private CircleImageView profile_pic;
        TextView breed_name,breedDescription,returnTxt,showTxt,menu_txt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            breed_name = itemView.findViewById(R.id.breed_name);
            breedDescription = itemView.findViewById(R.id.breedDescription);
            returnTxt = itemView.findViewById(R.id.returnTxt);
            showTxt = itemView.findViewById(R.id.showTxt);
            menu_txt = itemView.findViewById(R.id.menu_txt);
        }
    }

    private void getHistoryList(String id, ViewHolder holder, int position){

        String BASE_URL = "https://genofax.com/api/breed/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/

        Service service = retrofit.create(Service.class);
        Call<HistoryShowMainModel> call = service.getHistoryList(id);
        call.enqueue(new Callback<HistoryShowMainModel>() {
            @Override
            public void onResponse(Call<HistoryShowMainModel> call, Response<HistoryShowMainModel> response) {

                if (response.isSuccessful()){
                    holder.layout.setVisibility(View.VISIBLE);
                    dataClassList = response.body().getData();

                    /*id_pass = dataClassList.get(0).getId();
                    image_url = dataClassList.get(0).getImage_url();
                    breedHistoryResponseData= dataClassList.get(0).getResponse().getData();*/

                    String breedName = dataClassList.get(0).getResponse().getData().get(0).getBreed();
                    holder.breed_name.setText(breedName);

                    String breedDescription = String.valueOf(Html.fromHtml(dataClassList.get(0).getResponse().getClassification_summary()));
                    holder.breedDescription.setText(breedDescription);
                    /*Glide.with(mContext)
                            .load(dataClassList.get(0).getImage_url())
                            .into(holder.profile_pic);*/
                    Picasso.get().load(dataClassList.get(0).getImage_url())
                            .into(holder.profile_pic);

                }else {

                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryShowMainModel> call, Throwable t) {
                Toast.makeText(mContext, "False", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
