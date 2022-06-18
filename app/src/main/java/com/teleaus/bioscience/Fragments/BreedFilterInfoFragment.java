package com.teleaus.bioscience.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.R;

public class BreedFilterInfoFragment extends Fragment {
    Toolbar toolbarId;
    Button filterButton;
    private FirebaseAnalytics mFBanalytics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_breed_filter_info, container, false);
        initialise(view);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment filterFragment = new FilterFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, filterFragment)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "BreedFilterScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "BreedFilterScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        filterButton = view.findViewById(R.id.filterButton);
        toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}