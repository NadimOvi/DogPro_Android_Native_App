package com.teleaus.bioscience.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.Adapter.BreedRecentHistoryAdapter;
import com.teleaus.bioscience.DemoImageHomeAdapter;
import com.teleaus.bioscience.DemoImageHomeModel;
import com.teleaus.bioscience.MapsView.MapsActivity;
import com.teleaus.bioscience.Model.BreedHistoryData;
import com.teleaus.bioscience.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageView ClickMenu,ClickClose;
    TextView clickProfile,clickLogout;
    LinearLayout clickShare,clickFollowFacebook, clickContactUs,clickAboutApp,rateThisApp,petClinic;
    DrawerLayout drawerLayout;
    View view;
    RecyclerView popularBreedRecyclerView;
    ArrayList<BreedHistoryData> breedHistoryLists;
    List<DemoImageHomeModel> demoImageHomeModels;
    private BreedRecentHistoryAdapter.RecyclerViewClickListener listener;
    private DemoImageHomeAdapter.RecyclerViewClickListener listenerDemo;

    private FirebaseAnalytics mFBanalytics;

    private ReviewInfo reviewInfo;
    private ReviewManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initialise(view);


        if (isConnected()){
            Bundle extras = getArguments();
            breedHistoryLists  = extras.getParcelableArrayList("breedHistoryLists");

            breedListViewPager(view);
            setupListViewPager(view);

            /*mAppUpdateManager = AppUpdateManagerFactory.create(getActivity());
            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo result) {
                    if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                        try {
                            mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,getActivity(),RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mAppUpdateManager.registerListener(installStateUpdatedListener);*/
            //////

            /*activateReviewInfo();*/
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


        ClickMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);

            }
        });
        ClickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });

        return view;
    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }else if (requestCode == RC_APP_UPDATE && resultCode !=RESULT_OK){
            Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
        }

    }
*/

  /*  private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED){
                showCompletedUpdated();
            }
        }
    };

    private void showCompletedUpdated() {
        Snackbar snackbar = Snackbar.make(view.findViewById(android.R.id.content),"New app is ready",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    public void onStop() {
        if (mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }*/


    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }
    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "HomeScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        ClickMenu = view.findViewById(R.id.ClickMenu);
        ClickClose = view.findViewById(R.id.ClickClose);
        drawerLayout = view.findViewById(R.id.drawer_layout);

        /*popularBreedRecyclerView = view.findViewById(R.id.popularBreedRecyclerView);
        popularBreedRecyclerView.setHasFixedSize(true);
        popularBreedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));*/

       /* clickProfile = view.findViewById(R.id.clickProfile);*/
        clickShare = view.findViewById(R.id.clickShare);
        petClinic = view.findViewById(R.id.petClinic);
        /*clickFollowFacebook = view.findViewById(R.id.clickFollowFacebook);*/
        clickContactUs = view.findViewById(R.id.clickContactUs);
        clickAboutApp = view.findViewById(R.id.clickAboutApp);
        rateThisApp = view.findViewById(R.id.rateThisApp);
       /* clickLogout = view.findViewById(R.id.clickLogout);*/


        petClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*MapsFragment mapsFragment = new MapsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, mapsFragment)
                        .addToBackStack(null).commit();*/

                Intent intent= new Intent(getActivity() , MapsActivity.class);
                startActivity(intent);
            }
        });

        clickShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle("Chooser title")
                        .setText("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                        .startChooser();
            }
        });

        clickContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, contactUsFragment)
                        .addToBackStack(null).commit();
            }
        });
        clickAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, aboutAppFragment)
                        .addToBackStack(null).commit();
            }
        });
        rateThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewFlow();
            }
        });
    }


    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
        view.setClickable(false);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            view.setClickable(true);
        }
    }

    private void setupListViewPager(View view){
        ViewPager2 viewPager2 = view.findViewById(R.id.imagePageViewer);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        clickListenerForDemo(demoImageHomeModels);
        viewPager2.setAdapter(new DemoImageHomeAdapter(getModelList(),getContext(),listenerDemo));
    }



    private List<DemoImageHomeModel> getModelList(){
        List<DemoImageHomeModel> demoImageHomeModels = new ArrayList<>();

        DemoImageHomeModel first = new DemoImageHomeModel();
        first.poster = R.drawable.first_image;
        first.text = "Identify your dog's breed";
        demoImageHomeModels.add(first);

        DemoImageHomeModel second = new DemoImageHomeModel();
        second.poster = R.drawable.second_image;
        second.text = "Search breeds based on different dog attributes";
        demoImageHomeModels.add(second);

        DemoImageHomeModel third = new DemoImageHomeModel();
        third.poster = R.drawable.third_image;
        third.text = "Learn about different dog breeds";
        demoImageHomeModels.add(third);

        return demoImageHomeModels;
    }

    private void breedListViewPager(View view){
        ViewPager2 viewPager2 = view.findViewById(R.id.breedPageViewer);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        clickListener(breedHistoryLists);
        viewPager2.setAdapter(new BreedRecentHistoryAdapter(breedHistoryLists,getContext(),listener));
    }

    private void clickListener(ArrayList<BreedHistoryData> list) {
        listener = new BreedRecentHistoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                BreedRecentIndividualFragment breedRecentIndividualFragment = new BreedRecentIndividualFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",list.get(position).getId());
                bundle.putString("image_url",list.get(position).getImage_url());
                bundle.putString("classification",list.get(position).getResponse().getClassification_summary());
                bundle.putParcelableArrayList("list",list.get(position).getResponse().getData());

                breedRecentIndividualFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, breedRecentIndividualFragment)
                        .addToBackStack(null).commit();
            }
        };
    }
    private void clickListenerForDemo(List<DemoImageHomeModel> demoImageHomeModels) {
        listenerDemo = new DemoImageHomeAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0){

                    Bundle bundle = new Bundle();
                    bundle.putString("CameraInfoClick","CameraInfoClick");
                    mFBanalytics.logEvent("CameraInfoClick",bundle);

                    CameraInfoFragment cameraInfoFragment = new CameraInfoFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, cameraInfoFragment)
                            .addToBackStack(null).commit();

                }else if (position == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("FilterScreenClick","FilterScreenClick");
                    mFBanalytics.logEvent("FilterScreenClick",bundle);

                    BreedFilterInfoFragment breedFilterInfoFragment = new BreedFilterInfoFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, breedFilterInfoFragment)
                            .addToBackStack(null).commit();

                }else if (position == 2){
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "BreedListInfoScreen");
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "BreedListInfoScreen");
                    mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

                    BreedListInfoFragment breedListInfoFragment = new BreedListInfoFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, breedListInfoFragment)
                            .addToBackStack(null).commit();

                }
            }
        };
    }

    void activateReviewInfo()
    {
        manager = ReviewManagerFactory.create(getActivity());
        Task<ReviewInfo> managerInfoTask = manager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener((task)->
        {
            if(task.isSuccessful())
            {
                reviewInfo = task.getResult();
            }
            else
            {
                Toast.makeText(getActivity(), "Review failed to start", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void startReviewFlow()
    {
        /*if(reviewInfo !=null)
        {
            Task<Void> flow= manager.launchReviewFlow(getActivity(),reviewInfo);
            flow.addOnCompleteListener(task ->
            {
                Toast.makeText(getActivity(), "Rating is completed", Toast.LENGTH_SHORT).show();
            });
        }*/
        Uri uri = Uri.parse("market://details?id="+getActivity().getPackageName());
        Intent goTo= new Intent(Intent.ACTION_VIEW,uri);

        try{
            startActivity(goTo);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getActivity(), "Unable to open\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



}