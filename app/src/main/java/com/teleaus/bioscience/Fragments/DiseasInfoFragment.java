package com.teleaus.bioscience.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Adapter.AllDogBreedListAdapter;
import com.teleaus.bioscience.Adapter.AssociatedDiseasesAdapter;
import com.teleaus.bioscience.Adapter.DiseaseListAdapter;
import com.teleaus.bioscience.Model.Associated_diseases;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.Model.DiseaseInfoModel;
import com.teleaus.bioscience.Model.getBreedHistoryListModel;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiseasInfoFragment extends Fragment {

    TextView diseaseNameTools,anotherNameShow,diseaseDescriptionShow,diseaseSymptomShow,diseaseDiagnoseShow,treatMethodShow,diseaseNameAgain,otherNameShow;
    Toolbar toolbarId;
    CardView moreDetailsCard;
    AssociatedDiseasesAdapter associatedDiseasesAdapter;
    ArrayList<DataList> listResult;
    ArrayList<Associated_diseases> associated_diseasesArrayList;
    int diseaseId;
    String diseaseName;
    RecyclerView diseaseRecyclerView;
    String BASE_URL = "https://api.genofax.com/";
    String token = "FE:38:E1:DA:42:34:BB:83:F3:1C:B8:27:75:9F:75:80:98:A8:24:93";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_diseas_info, container, false);
        initialise(view);

        Bundle extras = getArguments();
        listResult  = extras.getParcelableArrayList("dataList");
        diseaseId = extras.getInt("diseaseId");
        diseaseName = extras.getString("diseaseName");
        diseaseNameTools.setText(diseaseName);

        getDiseaseList();

        return view;
    }

    private void initialise(View view) {
        anotherNameShow = view.findViewById(R.id.anotherNameShow);
        diseaseNameTools = view.findViewById(R.id.diseaseNameTools);
        diseaseDescriptionShow = view.findViewById(R.id.diseaseDescriptionShow);
        diseaseSymptomShow = view.findViewById(R.id.diseaseSymptomShow);
        diseaseDiagnoseShow = view.findViewById(R.id.diseaseDiagnoseShow);
        treatMethodShow = view.findViewById(R.id.treatMethodShow);
        moreDetailsCard = view.findViewById(R.id.moreDetailsCard);
        diseaseRecyclerView = view.findViewById(R.id.diseaseRecyclerView);
        diseaseRecyclerView.setHasFixedSize(true);
        diseaseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

       /* diseaseNameAgain = view.findViewById(R.id.diseaseNameAgain);
        otherNameShow = view.findViewById(R.id.otherNameShow);*/


        toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getActivity().onBackPressed();*/
                BreedListFragment breedListFragment = new BreedListFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dataLists", listResult);
                breedListFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, breedListFragment)
                        .addToBackStack("tag").commit();
            }
        });
    }

    private void getDiseaseList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<DiseaseInfoModel> call = service.getDiseaseInfoModel(String.valueOf(diseaseId),token);
        call.enqueue(new Callback<DiseaseInfoModel>() {
            @Override
            public void onResponse(Call<DiseaseInfoModel> call, Response<DiseaseInfoModel> response) {
                if (response.isSuccessful()){
                    DiseaseInfoModel diseaseInfoModel = response.body();
                    anotherNameShow.setText(diseaseInfoModel.getAlternative_name());
                    diseaseDescriptionShow.setText(diseaseInfoModel.getDescription());
                    diseaseSymptomShow.setText(diseaseInfoModel.getSymptom());
                    diseaseDiagnoseShow.setText(diseaseInfoModel.getDiagnosis());
                    treatMethodShow.setText(diseaseInfoModel.getTreat_method());
                    associated_diseasesArrayList = diseaseInfoModel.getAssociated_diseases();
                    if (associated_diseasesArrayList==null){

                        moreDetailsCard.setVisibility(View.GONE);
                    }else{
                        showAdapter(associated_diseasesArrayList);
                    }



                }else{
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiseaseInfoModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showAdapter(ArrayList<Associated_diseases> listResult) {
        if (!listResult.isEmpty()){
            associatedDiseasesAdapter = new AssociatedDiseasesAdapter(listResult,getContext());
            diseaseRecyclerView.setAdapter(associatedDiseasesAdapter);
        }else{
            /*Toast.makeText(getActivity(), "Server is not Working", Toast.LENGTH_SHORT).show();*/
            Log.e("msg","Not found");
        }
    }
}