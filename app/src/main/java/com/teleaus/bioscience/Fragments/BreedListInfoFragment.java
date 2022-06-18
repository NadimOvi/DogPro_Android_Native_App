package com.teleaus.bioscience.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.MainActivity;
import com.teleaus.bioscience.Model.FilterModelClass;
import com.teleaus.bioscience.Model.getBreedHistoryListModel;
import com.teleaus.bioscience.Model.getBreedHistoryListModelData;
import com.teleaus.bioscience.Model.getPhysicalAttibutesDataModel;
import com.teleaus.bioscience.Model.getPhysicalAttibutesModel;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedListInfoFragment extends Fragment {
    Toolbar toolbarId;
    ImageButton getBreedHistoryList,getPhysicalAttributesList;
    String BASE_URL ="https://api.genofax.com/";
    String token = "FE:38:E1:DA:42:34:BB:83:F3:1C:B8:27:75:9F:75:80:98:A8:24:93";
    ArrayList<getBreedHistoryListModelData> getBreedHistoryListModelDataArrayList;
    ArrayList<getPhysicalAttibutesDataModel> getPhysicalAttibutesDataModelArrayList;

    private FirebaseAnalytics mFBanalytics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breed_list_info, container, false);
        initialise(view);



        getBreedHistoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Learn_Breed_History_Click","Learn_Breed_History_Click");
                mFBanalytics.logEvent("Learn_Breed_History_Click",bundle);
                getBreedList();
            }
        });

        getPhysicalAttributesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Learn_Breed_Physical_Attributes_Click","Learn_Breed_Physical_Attributes_Click");
                mFBanalytics.logEvent("Learn_Breed_Physical_Attributes_Click",bundle);
                getBreedAttributeList();
            }
        });


        return view;
    }


    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "BreedListInfoScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "BreedListInfoScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        getBreedHistoryList = view.findViewById(R.id.getBreedHistoryList);
        getPhysicalAttributesList = view.findViewById(R.id.getPhysicalAttributesList);
        toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getActivity().onBackPressed();*/
                Intent intent = new Intent(getActivity(),MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
    private void getBreedList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<getBreedHistoryListModel> call = service.getBreedHistory(token);
        call.enqueue(new Callback<getBreedHistoryListModel>() {
            @Override
            public void onResponse(Call<getBreedHistoryListModel> call, Response<getBreedHistoryListModel> response) {
                if (response.isSuccessful()){
                    getBreedHistoryListModelDataArrayList = response.body().getData();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("getBreedHistoryListModelDataArrayList", getBreedHistoryListModelDataArrayList);
                    LearnDogHistoryFragment learnDogHistoryFragment = new LearnDogHistoryFragment();
                    learnDogHistoryFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, learnDogHistoryFragment).commit();
                }else{
                    Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getBreedHistoryListModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBreedAttributeList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<getPhysicalAttibutesModel> call = service.getPhysicalAttibutes(token);
        call.enqueue(new Callback<getPhysicalAttibutesModel>() {
            @Override
            public void onResponse(Call<getPhysicalAttibutesModel> call, Response<getPhysicalAttibutesModel> response) {
                if (response.isSuccessful()){
                    getPhysicalAttibutesDataModelArrayList = response.body().getData();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("getPhysicalAttibutesDataModelArrayList", getPhysicalAttibutesDataModelArrayList);
                    LearnDogPhysicalFragment learnDogPhysicalFragment = new LearnDogPhysicalFragment();
                    learnDogPhysicalFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, learnDogPhysicalFragment).commit();

                }else{
                    Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getPhysicalAttibutesModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}