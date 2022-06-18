package com.teleaus.bioscience.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.LocalDatabase.HistoryDemoAdapter;
import com.teleaus.bioscience.LocalDatabase.HistoryDemoModel;
import com.teleaus.bioscience.LocalDatabase.HistoryShowAdapter;
import com.teleaus.bioscience.LocalDatabase.HistoryShowData;
import com.teleaus.bioscience.LocalDatabase.HistoryShowMainModel;
import com.teleaus.bioscience.LocalDatabase.MyDatabaseHelper;
import com.teleaus.bioscience.Model.BreedHistoryData;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<BreedHistoryData> breedHistoryLists;

    private MyDatabaseHelper dbHelper;
    /*private HistoryDemoAdapter adapter;*/
    private HistoryShowAdapter adapter;
    private String filter = "";

    String result="";
    private FirebaseAnalytics mFBanalytics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initialise(view);
        Bundle extras = getArguments();
        breedHistoryLists  = extras.getParcelableArrayList("breedHistoryLists");

        populaterecyclerView(filter/*,dataClassList*/);
        ArrayList<HistoryDemoModel> arr =dbHelper.peopleList(filter);

        List<String> arrayList = new ArrayList<>();

        for(int i=0;i<arr.size();i++){

            result+= arr.get(i).getIds()+",";
            arrayList.add(arr.get(i).getIds());
        }
        if(arr.size()>0){
            result=result.substring(0,result.length()-1);
        }
        /*Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();*/
      /*  getHistoryList(result);*/
        /*populaterecyclerView(filter*//*,dataClassList*//*);*/



        return view;
    }
    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "HomeScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }

    /*private void getHistoryList(String result){
        String BASE_URL = "https://genofax.com/api/breed/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        Service service = retrofit.create(Service.class);
        Call<HistoryShowMainModel> call = service.getHistoryList(result);
        call.enqueue(new Callback<HistoryShowMainModel>() {
            @Override
            public void onResponse(Call<HistoryShowMainModel> call, Response<HistoryShowMainModel> response) {

                if (response.isSuccessful()){
                    dataClassList = response.body().getData();

                }else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryShowMainModel> call, Throwable t) {
                Toast.makeText(getActivity(), "False", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void populaterecyclerView(String filter/*, ArrayList<HistoryShowData> dataClassList*/){
        dbHelper = new MyDatabaseHelper(getActivity());
       /* adapter = new HistoryDemoAdapter(dbHelper.peopleList(filter), getActivity(), recyclerView,);*/
        adapter = new HistoryShowAdapter(dbHelper.peopleList(filter), getActivity(), recyclerView/*,dataClassList*/);
        recyclerView.setAdapter(adapter);

    }
}