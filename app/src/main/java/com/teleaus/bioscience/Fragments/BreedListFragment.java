package com.teleaus.bioscience.Fragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Client;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Adapter.AllDogBreedListAdapter;
import com.teleaus.bioscience.Adapter.DiseaseListAdapter;
import com.teleaus.bioscience.Adapter.DogBreedResultAdapter;
import com.teleaus.bioscience.MainActivity;
import com.teleaus.bioscience.Model.Associated;
import com.teleaus.bioscience.Model.BreedInfoModel;
import com.teleaus.bioscience.Model.BreedMainModelList;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BreedListFragment extends Fragment {

    ArrayList<DataList> listResult;
    ArrayList<DataList> dataLists;
    RecyclerView breedListRecyclerView;
    SearchView searchView;

    AllDogBreedListAdapter allDogBreedListAdapter;
    DiseaseListAdapter diseaseListAdapter;
    private AllDogBreedListAdapter.RecyclerViewClickListener adapterListener;
    private DiseaseListAdapter.RecyclerViewClickListener DiseaseAdapterListener;
    String breedName,thumbnail;
    String BASE_URL = "https://api.genofax.com/";

    int diseaseId;
    String diseaseName;

    String token = "FE:38:E1:DA:42:34:BB:83:F3:1C:B8:27:75:9F:75:80:98:A8:24:93";

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    TextView breedNameShow, historyShow,countryName,lifeSpan,weightName,heightName,temperamentName,utilizationName;
    RecyclerView diseasesNameRecyclerView;
    CircleImageView imageUrlShow;
    Button button;
    private FirebaseAnalytics mFBanalytics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breed_list, container, false);

        initialise(view);
        if (isConnected()){
            Bundle extras = getArguments();
            listResult  = extras.getParcelableArrayList("dataLists");
            if (listResult==null){
                postAgain();
            }else{
                showAdapter(listResult);
            }
        }else{
            new iOSDialogBuilder(getContext())
                    .setTitle("Alert!")
                    .setSubtitle("No Internet Connection from your device")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();

                        }
                    })
                    .build().show();
        }




        SearchManager searchManager =
                (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo =
                searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setSearchableInfo( searchableInfo);
        searchView.setQueryHint("Dog search");
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               /* allDogBreedListAdapter.getFilter().filter(s);
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();*/

                breedSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

              /* allDogBreedListAdapter.getFilter().filter(s);
               *//*adapterSetOnClickListener(listResult);*//*
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();*/
                breedSearch(s);
               return false;
            }
        });

        return view;
    }



    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "BreedListScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "BreedListScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
       /* filterButton = view.findViewById(R.id.filterButton);*/
        searchView = view.findViewById(R.id.searchView);
        breedListRecyclerView = view.findViewById(R.id.breedListRecyclerView);
        breedListRecyclerView.setHasFixedSize(true);
        breedListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
       /* toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);

        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });*/
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    private void showAdapter(ArrayList<DataList> listResult) {
        if (!listResult.isEmpty()){
            adapterSetOnClickListener(listResult);
            allDogBreedListAdapter = new AllDogBreedListAdapter(listResult,getContext(),adapterListener);
            breedListRecyclerView.setAdapter(allDogBreedListAdapter);
        }else{
            /*Toast.makeText(getActivity(), "Server is not Working", Toast.LENGTH_SHORT).show();*/
            Log.e("msg","Not found");
        }
    }
    private void adapterSetOnClickListener(final ArrayList<DataList> listResult) {
        adapterListener = new AllDogBreedListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                breedName = listResult.get(position).getName();
                thumbnail = listResult.get(position).getThumbnail();
                postCall(breedName);

            }
        };
    }

    private void postCall(String breedName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<BreedInfoModel> call = service.postBreedListInfo(breedName);
        call.enqueue(new Callback<BreedInfoModel>() {
            @Override
            public void onResponse(Call<BreedInfoModel> call, Response<BreedInfoModel> response) {
                if (response.isSuccessful()){
                    BreedInfoModel breedInfoModel = response.body();
                    String breedName = breedInfoModel.getBreed();
                    String breedThumbnail = breedInfoModel.getThumbnail();
                    String breedCountryOfOrigin = breedInfoModel.getCountry_of_origin();
                    String breedHistory = breedInfoModel.getBrief_history();
                    ArrayList<String> breedLifeSpan = breedInfoModel.getLife_span();
                    ArrayList<String> breedIdeal_weight = breedInfoModel.getIdeal_weight();
                    ArrayList<String> breedIdeal_height = breedInfoModel.getIdeal_height();
                    ArrayList<String> breedTemperament = breedInfoModel.getTemperament();
                    String breedCountry_of_patronage = breedInfoModel.getCountry_of_patronage();
                    ArrayList<String> breedUtilization = breedInfoModel.getUtilization();
                    ArrayList<String> breedAlternative_names = breedInfoModel.getAlternative_names();
                    String breedFci_classification = breedInfoModel.getFci_classification();
                    ArrayList<Associated> associated_diseases = breedInfoModel.getAssociated_diseases();

                    createPopUpDialog(breedName,breedThumbnail,breedCountryOfOrigin,breedHistory,breedLifeSpan,breedIdeal_weight,breedIdeal_height,
                            breedTemperament,breedCountry_of_patronage,breedUtilization,breedAlternative_names,breedFci_classification,associated_diseases);

                }else{
                    Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BreedInfoModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPopUpDialog(String breedName, String breedThumbnail, String breedCountryOfOrigin, String breedHistory, ArrayList<String> breedLifeSpan,
                                   ArrayList<String> breedIdeal_weight, ArrayList<String> breedIdeal_height, ArrayList<String> breedTemperament,
                                   String breedCountry_of_patronage, ArrayList<String> breedUtilization, ArrayList<String> breedAlternative_names,
                                   String breedFci_classification, ArrayList<Associated> associated_diseases) {

        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.popup_view_details,null);
        breedNameShow = popUp_card.findViewById(R.id.breedNameShow);
        historyShow = popUp_card.findViewById(R.id.historyShow);
        imageUrlShow = popUp_card.findViewById(R.id.imageUrlShow);
        button = popUp_card.findViewById(R.id.button);
        countryName = popUp_card.findViewById(R.id.countryName);
        lifeSpan = popUp_card.findViewById(R.id.lifeSpan);
        weightName = popUp_card.findViewById(R.id.weightName);
        heightName = popUp_card.findViewById(R.id.heightName);
        temperamentName = popUp_card.findViewById(R.id.temperamentName);
        utilizationName = popUp_card.findViewById(R.id.utilizationName);

        diseasesNameRecyclerView = popUp_card.findViewById(R.id.diseasesNameRecyclerView);
        diseasesNameRecyclerView.setHasFixedSize(true);
        diseasesNameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        diseasesAdapter(associated_diseases);


        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(true);
        dialog = dialogBuilder.create();
        dialog.show();

        Glide.with(this)
                .load(breedThumbnail)
                .into(imageUrlShow);
        historyShow.setText(breedHistory);
        countryName.setText(breedCountryOfOrigin);
        breedNameShow.setText(breedName);

        StringBuilder lifeSpanbuilder = new StringBuilder();
        for (String details : breedLifeSpan) {
            lifeSpanbuilder.append(details + "\n");
        }
        lifeSpan.setText(lifeSpanbuilder.toString());

        StringBuilder ideal_weightBuilder = new StringBuilder();
        for (String details : breedIdeal_weight) {
            ideal_weightBuilder.append(details + "\n");
        }
        weightName.setText(ideal_weightBuilder.toString());

        StringBuilder ideal_heightBuilder = new StringBuilder();
        for (String details : breedIdeal_height) {
            ideal_heightBuilder.append(details + "\n");
        }
        heightName.setText(ideal_heightBuilder.toString());

        StringBuilder temperamentBuilder = new StringBuilder();
        for (String details : breedTemperament) {
            temperamentBuilder.append(details + "\n");
        }
        temperamentName.setText(temperamentBuilder.toString());

        StringBuilder utilizationBuilder = new StringBuilder();
        for (String details : breedUtilization) {
            utilizationBuilder.append(details + "\n");
        }
        utilizationName.setText(utilizationBuilder.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void diseasesAdapter(ArrayList<Associated> list) {
        if (!listResult.isEmpty()){
            diseasesAdapterSetOnClickListener(list,listResult);
            diseaseListAdapter = new DiseaseListAdapter(list,getContext(),DiseaseAdapterListener);
            diseasesNameRecyclerView.setAdapter(diseaseListAdapter);

        }else{
            /*Toast.makeText(getActivity(), "Server is not Working", Toast.LENGTH_SHORT).show();*/
            Log.e("msg","Not found");
        }
    }


    private void diseasesAdapterSetOnClickListener(final ArrayList<Associated> listResult, ArrayList<DataList> dataList) {
        DiseaseAdapterListener = new DiseaseListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                diseaseId = listResult.get(position).getDisease_id();
                diseaseName = listResult.get(position).getDisease_name();

                /*Toast.makeText(getActivity(), diseaseName, Toast.LENGTH_SHORT).show();*/

                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putInt("diseaseId",diseaseId);
                bundle.putString("diseaseName",diseaseName);
                bundle.putParcelableArrayList("dataList", dataList);
                DiseasInfoFragment diseasInfoFragment = new DiseasInfoFragment();
                diseasInfoFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, diseasInfoFragment).commit();


            }
        };
    }


    private void postAgain() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/
        Service service = retrofit.create(Service.class);

        Call<BreedMainModelList> call = service.postBreedList(token);
        call.enqueue(new Callback<BreedMainModelList>() {
            @Override
            public void onResponse(Call<BreedMainModelList> call, Response<BreedMainModelList> response) {

                if (response.isSuccessful()){
                    dataLists = response.body().getData();
                    showAdapter(dataLists);
                } else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BreedMainModelList> call, Throwable t) {

                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void breedSearch(String s){
        Bundle bundle = new Bundle();
        bundle.putString("BreedSearchClick","BreedSearchClick");
        mFBanalytics.logEvent("BreedSearchClick",bundle);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/
        Service service = retrofit.create(Service.class);

        Call<BreedMainModelList> call = service.postBreedListSearch(s,token);
        call.enqueue(new Callback<BreedMainModelList>() {
            @Override
            public void onResponse(Call<BreedMainModelList> call, Response<BreedMainModelList> response) {

                if (response.isSuccessful()){
                    dataLists = response.body().getData();
                    showAdapter(dataLists);
                } else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BreedMainModelList> call, Throwable t) {

                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}