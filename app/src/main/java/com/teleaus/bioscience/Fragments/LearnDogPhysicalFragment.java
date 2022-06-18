package com.teleaus.bioscience.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.teleaus.bioscience.Adapter.LearnDogBreedPhysicalAdapter;
import com.teleaus.bioscience.Adapter.LearnDogHistoryAdapter;
import com.teleaus.bioscience.Model.getBreedHistoryListModelData;
import com.teleaus.bioscience.Model.getPhysicalAttibutesDataModel;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

public class LearnDogPhysicalFragment extends Fragment {
    Toolbar toolbarId;
    RecyclerView learnDogRecylerView;
    ArrayList<getPhysicalAttibutesDataModel> getPhysicalAttibutesDataModelArrayList;
    private LearnDogBreedPhysicalAdapter.RecyclerViewClickListener adapterListener;

    String breedNameShow,thumbnailShow;
    ArrayList<String> life_span,ideal_weight,ideal_height,temperament,utilization;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    TextView nameShow,countryName,lifeSpan,weightName,heightName,temperamentName,utilizationName;
    ImageView imageUrlShow;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn_dog_physical, container, false);
        Bundle extras = getArguments();
        initialise(view);
        getPhysicalAttibutesDataModelArrayList  = extras.getParcelableArrayList("getPhysicalAttibutesDataModelArrayList");

        showAdapter(getPhysicalAttibutesDataModelArrayList);
        return view;
    }
    private void initialise(View view) {
        toolbarId = view.findViewById(R.id.toolbarId);
        learnDogRecylerView = view.findViewById(R.id.learnDogRecylerView);
        learnDogRecylerView.setHasFixedSize(true);
        learnDogRecylerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* getActivity().onBackPressed();*/
                BreedListInfoFragment breedListInfoFragment = new BreedListInfoFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, breedListInfoFragment)
                        .addToBackStack("tag").commit();

            }
        });
    }
    private void showAdapter(ArrayList<getPhysicalAttibutesDataModel> listResult) {
        if (!listResult.isEmpty()){
            adapterSetOnClickListener(listResult);
            LearnDogBreedPhysicalAdapter learnDogBreedPhysicalAdapter = new LearnDogBreedPhysicalAdapter(listResult,getContext(),adapterListener);
            learnDogRecylerView.setAdapter(learnDogBreedPhysicalAdapter);
        }
    }
    private void adapterSetOnClickListener(final ArrayList<getPhysicalAttibutesDataModel> listResult) {
        adapterListener = new LearnDogBreedPhysicalAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                breedNameShow = listResult.get(position).getBreed();
                thumbnailShow = listResult.get(position).getThumbnail();
                life_span = listResult.get(position).getLife_span();
                ideal_height = listResult.get(position).getIdeal_height();
                ideal_weight = listResult.get(position).getIdeal_weight();
                /*createPopUpDialog(breedNameShow,thumbnailShow,life_span,ideal_height,ideal_weight);*/
            }
        };
    }
    private void createPopUpDialog(String breedNameShow, String thumbnail, ArrayList<String> life_span, ArrayList<String> ideal_height, ArrayList<String> ideal_weight) {
        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.popup_learn_dog_physical_view,null);


        nameShow = popUp_card.findViewById(R.id.nameShow);
        imageUrlShow = popUp_card.findViewById(R.id.imageUrlShow);
        button = popUp_card.findViewById(R.id.button);
        countryName = popUp_card.findViewById(R.id.countryName);
        lifeSpan = popUp_card.findViewById(R.id.lifeSpan);
        weightName = popUp_card.findViewById(R.id.weightName);
        heightName = popUp_card.findViewById(R.id.heightName);


        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(true);
        dialog = dialogBuilder.create();
        dialog.show();



        Glide.with(this)
                .load(thumbnail)
                .into(imageUrlShow);
        nameShow.setText(breedNameShow);


        StringBuilder lifeSpanbuilder = new StringBuilder();
        for (String details : this.life_span) {
            lifeSpanbuilder.append(details + "\n");
        }
        lifeSpan.setText(lifeSpanbuilder.toString());

        StringBuilder ideal_weightBuilder = new StringBuilder();
        for (String details : this.ideal_weight) {
            ideal_weightBuilder.append(details + "\n");
        }
        weightName.setText(ideal_weightBuilder.toString());

        StringBuilder ideal_heightBuilder = new StringBuilder();
        for (String details : this.ideal_height) {
            ideal_heightBuilder.append(details + "\n");
        }
        heightName.setText(ideal_heightBuilder.toString());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}