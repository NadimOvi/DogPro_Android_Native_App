package com.teleaus.bioscience.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Adapter.FilterSearchAdapter;
import com.teleaus.bioscience.Adapter.HistoryDogBreedResultAdapter;
import com.teleaus.bioscience.LocalDatabase.QueryModels;
import com.teleaus.bioscience.Model.BreedHistoryResponseData;
import com.teleaus.bioscience.Model.FilterBreedData;
import com.teleaus.bioscience.Model.FilterModelClass;
import com.teleaus.bioscience.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class FilterFragment extends Fragment {

    Spinner heightSpinner,weightSpinner,temperamentSpinner,utilizationSpinner;
    ImageButton filterButton;
    Button searchButton;
    LinearLayout hiddenView;
    Toolbar toolbarId;
    private FilterSearchAdapter.RecyclerViewClickListener adapterListener;
    String URL = "https://api.genofax.com/query_parameters/";
    String BASE_URL = "https://api.genofax.com/";
    String token = "FE:38:E1:DA:42:34:BB:83:F3:1C:B8:27:75:9F:75:80:98:A8:24:93";
    ChipGroup chipGroup;
    ChipGroup secondChipGroup;
    ArrayList<String> listOfKeys;
    HashMap<String, List<Integer>> name;
    ArrayList<List<Integer>> heightValuesArrayList;
    ArrayList<List<Integer>> weightValuesArrayList;
    ArrayList<String> temperamentList;
    ArrayList<String> utilizationList;
    List<Integer> heightList,weightList;
    //////chip
    ArrayList<String> temperamentArray=new ArrayList<>();
    ArrayList<String> utilizationArray=new ArrayList<>();
    RecyclerView searchFilter;

    //////
    String breedName,thumbnail,country_of_origin,brief_history;
    ArrayList<String> life_span,ideal_weight,ideal_height,temperament,utilization;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    TextView breedNameShow, historyShow,countryName,lifeSpan,weightName,heightName,temperamentName,utilizationName;
    ImageView imageUrlShow;
    Button button;
    private FirebaseAnalytics mFBanalytics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        initialise(view);

        /*fieldList(URL);*/

        if (isConnected()){
            retrofitCall();
            filterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hiddenView.getVisibility() == View.VISIBLE) {

                        TransitionManager.beginDelayedTransition(hiddenView,
                                new AutoTransition());
                        hiddenView.setVisibility(View.GONE);
                        filterButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    }

                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    else {

                        TransitionManager.beginDelayedTransition(hiddenView,
                                new AutoTransition());
                        hiddenView.setVisibility(View.VISIBLE);
                        filterButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    }
                }
            });
            heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getItemAtPosition(i).equals("Select an item from list")){
                        /* Toast.makeText(adapterView.getContext(),"Working", Toast.LENGTH_SHORT).show();*/
                    }else {
                        String item = adapterView.getItemAtPosition(i).toString();
                        /*Toast.makeText(adapterView.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();*/
                    }
                    String height = String.valueOf(heightSpinner.getItemAtPosition(heightSpinner.getSelectedItemPosition()));
                    heightList = heightValuesArrayList.get(i);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String weight = String.valueOf(weightSpinner.getItemAtPosition(weightSpinner.getSelectedItemPosition()));
                    weightList = weightValuesArrayList.get(i);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            temperamentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String temperament = String.valueOf(temperamentSpinner.getItemAtPosition(temperamentSpinner.getSelectedItemPosition()));
                    /* Toast.makeText(getActivity(), temperament , Toast.LENGTH_SHORT).show();*/
                    if (temperament.equals("Select an item from list")){
                        Log.d(TAG, "onItemSelected:");
                    }else {
                        temperamentArray.add(temperament+"");
                        final Chip chip = new Chip(getActivity());
                        ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getActivity(),
                                null,
                                0,
                                R.style.Widget_MaterialComponents_Chip_Entry);
                        chip.setChipDrawable(chipDrawable);
                        chip.setText(temperament+"");
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chipGroup.removeView(chip);
                                temperamentArray.remove(chip.getText());
                                /*Toast.makeText(getActivity(),arr.size()+"",Toast.LENGTH_LONG).show();*/
                            }
                        });
                        chipGroup.addView(chip);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            utilizationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String utilization = String.valueOf(utilizationSpinner.getItemAtPosition(utilizationSpinner.getSelectedItemPosition()));

                    if (utilization.equals("Select an item from list")){
                        Log.d(TAG, "onItemSelected:");
                    }else {
                        utilizationArray.add(utilization+"");
                        final Chip chip = new Chip(getActivity());
                        ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getActivity(),
                                null,
                                0,
                                R.style.Widget_MaterialComponents_Chip_Entry);
                        chip.setChipDrawable(chipDrawable);
                        chip.setText(utilization+"");
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                secondChipGroup.removeView(chip);
                                utilizationArray.remove(chip.getText());
                                /*Toast.makeText(getActivity(),arr.size()+"",Toast.LENGTH_LONG).show();*/
                            }
                        });
                        secondChipGroup.addView(chip);
                    }



                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("FilterSearchClick","FilterSearchClick");
                    mFBanalytics.logEvent("FilterSearchClick",bundle);


                    if (isConnected()){
                        if (heightList.isEmpty()&&weightList.isEmpty()&&temperamentArray.isEmpty()&&utilizationArray.isEmpty()){
                            Toast.makeText(getActivity(), "Please Insert a item", Toast.LENGTH_SHORT).show();
                        }else if(!heightList.isEmpty()&&!weightList.isEmpty()&&!temperamentArray.isEmpty()){
                            querySearchWithHeightAndWeightAndTemperament();
                        }else if(!heightList.isEmpty()&&!weightList.isEmpty()&&!utilizationArray.isEmpty()){
                            querySearchWithHeightAndWeightAndUtilization();
                        }else{
                            querySearchAll();
                        }
                    }else {
                        new iOSDialogBuilder(getContext())
                                .setTitle("Alert!")
                                .setSubtitle("No Internet Connection from your device")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Ok",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();
                                        getActivity().recreate();
                                    }
                                })
                                .build().show();
                    }


               /* if(!heightList.isEmpty()){
                    querySearchWithHeight();
                  *//*  Toast.makeText(getActivity(), "Search height", Toast.LENGTH_LONG).show();*//*
                }
                if(!weightList.isEmpty()){
                    querySearchWithWeight();
                    *//*Toast.makeText(getActivity(), "Search weight", Toast.LENGTH_LONG).show();*//*
                }*/

                /*if(!temperamentArray.isEmpty()){
                    querySearchWithTemperament();
                    *//*Toast.makeText(getActivity(), "Search tem", Toast.LENGTH_LONG).show();*//*
                }
                if(!utilizationArray.isEmpty()){
                    querySearchWithUtilization();
                   *//* Toast.makeText(getActivity(), "Search uti", Toast.LENGTH_LONG).show();*//*
                }
                if(!heightList.isEmpty()&&!weightList.isEmpty()){

                    querySearchWithHeightAndWeight();
                    *//*Toast.makeText(getActivity(), "Search height and weight", Toast.LENGTH_LONG).show();*//*
                }
                if(!heightList.isEmpty()&&!temperamentArray.isEmpty()){

                    querySearchWithHeightAndTemperament();
                    *//*Toast.makeText(getActivity(), "Search height and tem", Toast.LENGTH_LONG).show();*//*
                }
                if(!heightList.isEmpty()&&!utilizationArray.isEmpty()){

                    querySearchWithHeightAndUtilization();
                    *//*Toast.makeText(getActivity(), "Search height and uti", Toast.LENGTH_LONG).show();*//*
                }
                if(!weightList.isEmpty()&&!temperamentArray.isEmpty()){

                    querySearchWithWeightAndTemperament();
                  *//*  Toast.makeText(getActivity(), "Search weight and tem", Toast.LENGTH_LONG).show();*//*
                }
                if(!weightList.isEmpty()&&!utilizationArray.isEmpty()){

                    querySearchWithWeightAndUtilization();
                    *//*Toast.makeText(getActivity(), "Search weight and Uti", Toast.LENGTH_LONG).show();*//*
                }
                if(!temperamentArray.isEmpty()&&!utilizationArray.isEmpty()){

                    querySearchWithTemperamentAndUtilization();
                    *//*Toast.makeText(getActivity(), "Search tem and Uti", Toast.LENGTH_LONG).show();*//*
                }*/
                    /*if (!heightList.isEmpty()&&!weightList.isEmpty()&&!temperamentArray.isEmpty()&&!utilizationArray.isEmpty()){


                     *//*Toast.makeText(getActivity(), "Search All", Toast.LENGTH_LONG).show();*//*
                }*/


                }
            });


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
                            getActivity().recreate();

                        }
                    })
                    .build().show();
        }



        return view;
    }
    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "FilterScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "FilterScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        searchButton = view.findViewById(R.id.searchButton);
        heightSpinner = view.findViewById(R.id.heightSpinner);

        weightSpinner = view.findViewById(R.id.weightSpinner);
        temperamentSpinner = view.findViewById(R.id.temperamentSpinner);
        utilizationSpinner = view.findViewById(R.id.utilizationSpinner);
        toolbarId = view.findViewById(R.id.toolbarId);
        filterButton = view.findViewById(R.id.filterButton);
        chipGroup = view.findViewById(R.id.chipGroup);
        secondChipGroup = view.findViewById(R.id.secondChipGroup);
        hiddenView = view.findViewById(R.id.hiddenView);
        searchFilter = view.findViewById(R.id.searchFilter);
        searchFilter.setHasFixedSize(true);

        searchFilter.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);

        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
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

    private void retrofitCall (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<QueryModels> call = service.queryRetrofitCall(token);
        call.enqueue(new Callback<QueryModels>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<QueryModels> call, Response<QueryModels> response) {
                if (response.isSuccessful()){

                    HashMap<String, List<Integer>> heightList = response.body().getIdeal_heights_range();
                    HashMap<String, List<Integer>> weightList = response.body().getIdeal_weights_range();
                    temperamentList = response.body().getTemperaments();
                    utilizationList = response.body().getUtilization();
                    temperamentList.add(0,"Select an item from list");
                    utilizationList.add(0,"Select an item from list");

                    ArrayList<String> heightKeysArrayList= new ArrayList<String>(heightList.keySet());
                   /* heightKeysArrayList.add(0,"Select an item from list");*/
                    heightValuesArrayList = new ArrayList<List<Integer>>(heightList.values());


                    ArrayList<String> weightKeysArrayList= new ArrayList<String>(weightList.keySet());
                    /*weightKeysArrayList.add(0,"Select an item from list");*/
                    weightValuesArrayList = new ArrayList<List<Integer>>(weightList.values());


                    ArrayAdapter<String> hightAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.spinner_textview, heightKeysArrayList);
                    hightAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    heightSpinner.setAdapter(hightAdapter);
                    heightSpinner.setSelection(3);

                    ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.spinner_textview, weightKeysArrayList);
                    weightAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    weightSpinner.setAdapter(weightAdapter);
                    weightSpinner.setSelection(4);

                    ArrayAdapter<String> temperamentAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.spinner_textview, temperamentList);
                    weightAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    temperamentSpinner.setAdapter(temperamentAdapter);

                    ArrayAdapter<String> utilizationAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.spinner_textview, utilizationList);
                    weightAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

                    utilizationSpinner.setAdapter(utilizationAdapter);




                }else{
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                    retrofitCall();
                }
            }

            @Override
            public void onFailure(Call<QueryModels> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchAll(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String temperamentResult="";
        for(int i=0;i<temperamentArray.size();i++){
            temperamentResult+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            temperamentResult=temperamentResult.substring(0,temperamentResult.length()-1);
        }

        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String utilizationResult="";
        for(int i=0;i<utilizationArray.size();i++){
            utilizationResult+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            utilizationResult=utilizationResult.substring(0,utilizationResult.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchAll(token,heightList,weightList,temperamentArrayList,utilizationArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);

                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeightAndWeightAndTemperament(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String temperamentResult="";
        for(int i=0;i<temperamentArray.size();i++){
            temperamentResult+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            temperamentResult=temperamentResult.substring(0,temperamentResult.length()-1);
        }

        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String utilizationResult="";
        for(int i=0;i<utilizationArray.size();i++){
            utilizationResult+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            utilizationResult=utilizationResult.substring(0,utilizationResult.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeightAndWeightAndTemperament(token,heightList,weightList,temperamentArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);

                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeightAndWeightAndUtilization(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String temperamentResult="";
        for(int i=0;i<temperamentArray.size();i++){
            temperamentResult+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            temperamentResult=temperamentResult.substring(0,temperamentResult.length()-1);
        }

        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String utilizationResult="";
        for(int i=0;i<utilizationArray.size();i++){
            utilizationResult+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            utilizationResult=utilizationResult.substring(0,utilizationResult.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeightAndWeightAndUtilization(token,heightList,weightList,utilizationArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);

                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeight(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeight(token,heightList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithWeight(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithWeight(token,weightList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithTemperament(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<temperamentArray.size();i++){
            result+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithTemperament(token,temperamentArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithUtilization(){
        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<utilizationArray.size();i++){
            result+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithUtilization(token,/*heightList,weightList*/utilizationArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeightAndWeight(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeightAndWeight(token,heightList,weightList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeightAndTemperament(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<temperamentArray.size();i++){
            result+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeightAndTemperament(token,heightList,temperamentArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithHeightAndUtilization(){
        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<utilizationArray.size();i++){
            result+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithHeightAndUtilization(token,heightList,utilizationArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithWeightAndTemperament(){
        ArrayList<String> temperamentarrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<temperamentArray.size();i++){
            result+= temperamentArray.get(i)+",";

            temperamentarrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithWeightAndTemperament(token,weightList,temperamentarrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithWeightAndUtilization(){
        ArrayList<String> utilizationarrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<utilizationArray.size();i++){
            result+= utilizationArray.get(i)+",";

            utilizationarrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            result=result.substring(0,result.length()-1);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithWeightAndUtilization(token,weightList,utilizationarrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void querySearchWithTemperamentAndUtilization(){
        ArrayList<String> temperamentArrayList = new ArrayList<>();
        String temperamentResult="";
        for(int i=0;i<temperamentArray.size();i++){
            temperamentResult+= temperamentArray.get(i)+",";

            temperamentArrayList.add(temperamentArray.get(i));
        }
        if(temperamentArray.size()>0){
            temperamentResult=temperamentResult.substring(0,temperamentResult.length()-1);
        }

        ArrayList<String> utilizationArrayList = new ArrayList<>();
        String utilizationResult="";
        for(int i=0;i<utilizationArray.size();i++){
            utilizationResult+= utilizationArray.get(i)+",";

            utilizationArrayList.add(utilizationArray.get(i));
        }
        if(utilizationArray.size()>0){
            utilizationResult=utilizationResult.substring(0,utilizationResult.length()-1);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<FilterModelClass> call = service.querySearchWithTemperamentAndUtilization(token,temperamentArrayList,utilizationArrayList);
        call.enqueue(new Callback<FilterModelClass>() {
            @Override
            public void onResponse(Call<FilterModelClass> call, Response<FilterModelClass> response) {
                if (response.isSuccessful()){
                    ArrayList<FilterBreedData> filterBreedData = response.body().getBreeds();
                    showAdapter(filterBreedData);
                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModelClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAdapter(ArrayList<FilterBreedData> listResult) {
        if (!listResult.isEmpty()){
            adapterSetOnClickListener(listResult);
            FilterSearchAdapter filterSearchAdapter = new FilterSearchAdapter(listResult,getContext(),adapterListener);
            searchFilter.setAdapter(filterSearchAdapter);
        }
    }

    private void adapterSetOnClickListener(final ArrayList<FilterBreedData> listResult) {
        adapterListener = new FilterSearchAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                breedName = listResult.get(position).getBreed();
                thumbnail = listResult.get(position).getThumbnail();

                country_of_origin = listResult.get(position).getCountry_of_origin();
                brief_history = listResult.get(position).getBrief_history();
                life_span = listResult.get(position).getLife_span();
                ideal_weight = listResult.get(position).getIdeal_weight();
                ideal_height = listResult.get(position).getIdeal_height();
                temperament = listResult.get(position).getTemperament();
                utilization = listResult.get(position).getUtilization();

                /*Toast.makeText(getActivity(), breedName, Toast.LENGTH_SHORT).show();*/
                createPopUpDialog();
            }
        };
    }

    private void createPopUpDialog() {
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

        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(true);
        dialog = dialogBuilder.create();
        dialog.show();


        /*       breedName,thumbnail,country_of_origin,brief_history,life_span,ideal_weight,ideal_height,temperament;*/

        Glide.with(this)
                .load(thumbnail)
                .into(imageUrlShow);
        breedNameShow.setText(Html.fromHtml(breedName));
        historyShow.setText(Html.fromHtml(brief_history));
        countryName.setText(country_of_origin);


        StringBuilder lifeSpanbuilder = new StringBuilder();
        for (String details : life_span) {
            lifeSpanbuilder.append(details + "\n");
        }
        lifeSpan.setText(lifeSpanbuilder.toString());

        StringBuilder ideal_weightBuilder = new StringBuilder();
        for (String details : ideal_weight) {
            ideal_weightBuilder.append(details + "\n");
        }
        weightName.setText(ideal_weightBuilder.toString());

        StringBuilder ideal_heightBuilder = new StringBuilder();
        for (String details : ideal_height) {
            ideal_heightBuilder.append(details + "\n");
        }
        heightName.setText(ideal_heightBuilder.toString());

        StringBuilder temperamentBuilder = new StringBuilder();
        for (String details : temperament) {
            temperamentBuilder.append(details + "\n");
        }
        temperamentName.setText(temperamentBuilder.toString());


        StringBuilder utilizationBuilder = new StringBuilder();
        for (String details : utilization) {
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

}