package com.teleaus.bioscience.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.teleaus.bioscience.Adapter.AllDogBreedListAdapter;
import com.teleaus.bioscience.Adapter.LearnDogHistoryAdapter;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.Model.getBreedHistoryListModelData;
import com.teleaus.bioscience.Model.getPhysicalAttibutesDataModel;
import com.teleaus.bioscience.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class LearnDogHistoryFragment extends Fragment {
    Toolbar toolbarId;
    RecyclerView learnDogRecylerView;
    ArrayList<getBreedHistoryListModelData> getBreedHistoryListModelDataArrayList;
    private LearnDogHistoryAdapter.RecyclerViewClickListener adapterListener;
    String breedName,thumbnail,description;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    CircleImageView popup_imageUrlShow;
    TextView popup_NameShow,popup_HistoryShow;
    Button popup_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_learn_dog_history, container, false);
        initialise(view);
        Bundle extras = getArguments();
        getBreedHistoryListModelDataArrayList  = extras.getParcelableArrayList("getBreedHistoryListModelDataArrayList");

        showAdapter(getBreedHistoryListModelDataArrayList);
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
                /*getActivity().onBackPressed();*/
                BreedListInfoFragment breedListInfoFragment = new BreedListInfoFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, breedListInfoFragment)
                        .addToBackStack("tag").commit();

            }
        });
    }
    private void showAdapter(ArrayList<getBreedHistoryListModelData> listResult) {
        if (!listResult.isEmpty()){
            adapterSetOnClickListener(listResult);
            LearnDogHistoryAdapter learnDogHistoryAdapter = new LearnDogHistoryAdapter(listResult,getContext(),adapterListener);
            learnDogRecylerView.setAdapter(learnDogHistoryAdapter);
        }
    }
    private void adapterSetOnClickListener(final ArrayList<getBreedHistoryListModelData> listResult) {
        adapterListener = new LearnDogHistoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                breedName = listResult.get(position).getBreed_name();
                thumbnail = listResult.get(position).getThumbnail();
                description = listResult.get(position).getHistory();
                createPopUpDialog(breedName,thumbnail,description);
            }
        };
    }

    private void createPopUpDialog(String breedName, String thumbnail, String description) {

        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.popup_learn_dog_history_individual,null);

        popup_imageUrlShow = popUp_card.findViewById(R.id.popup_imageUrlShow);
        popup_Button = popUp_card.findViewById(R.id.popup_Button);
        popup_NameShow = popUp_card.findViewById(R.id.popup_NameShow);
        popup_HistoryShow = popUp_card.findViewById(R.id.popup_HistoryShow);


        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(true);
        dialog = dialogBuilder.create();
        dialog.show();

        Picasso.get().load(thumbnail).into(popup_imageUrlShow);
        popup_NameShow.setText(breedName);
        popup_HistoryShow.setText(description);
        popup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}