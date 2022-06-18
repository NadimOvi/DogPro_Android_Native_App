package com.teleaus.bioscience.Fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Adapter.DogBreedResultAdapter;
import com.teleaus.bioscience.BuildConfig;
import com.teleaus.bioscience.LocalDatabase.HistoryDemoModel;
import com.teleaus.bioscience.LocalDatabase.MyDatabaseHelper;
import com.teleaus.bioscience.MainActivity;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.io.File.separator;

public class ResultFragment extends Fragment {
    LinearLayout shareLayout;
    ConstraintLayout mainShareLayout;
    ImageView yourImage, chartImage,outputImage;
    TextView outputImageName,originName,lifeSpanShow;
    PieChart pieChart;
    RecyclerView recyclerView;
    Toolbar toolbarId;
    private DogBreedResultAdapter.RecyclerViewClickListener adapterListener;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    TextView breedNameShow, historyShow,countryName,lifeSpan,weightName,heightName,temperamentName,utilizationName;
    TextView breedNameTxt,percentageShowTxt;

    TextView demoText1;
    Button button,shareButton,returnButton,shareDialogButton,returnDialogButton;
    ImageButton yesButton,noButton,doNotKnowButton;
    ImageView coverImage;
    CircleImageView imageUrlShow;
    CircularImageView progressBarImage;
    String picture;
    PhotoView recyclerViewImage;

    private int currentProgress = 0;

    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    ArrayList<DataClass> listResult;
    String breedName,thumbnail,country_of_origin,brief_history;
    ArrayList<String> life_span,ideal_weight,ideal_height,temperament,utilization;
    boolean additional_info ;
    Uri imageURI;
    String breedPopUpName, PopUpPercentage, PopUpOrigin,imageId,imageUrl,classification;
    Bitmap bitmapCropImage;
    ArrayList<String> PopUpLifeSpan;
    private MyDatabaseHelper dbHelper;
    private FirebaseAnalytics mFBanalytics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Bundle extras = getArguments();
        listResult  = extras.getParcelableArrayList("dataClassList");
        imageURI = extras.getParcelable("resultUri");
        imageId = extras.getString("imageId");
        imageUrl = extras.getString("imageUrl");
        classification = extras.getString("classification");
        dbHelper = new MyDatabaseHelper(getActivity());

        initialise(view);
        setupPieChart();

        showChart(listResult,100);

        /*showDemo(listResult);*/

        showAdapter(listResult);

        progressBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.image_view_controller,null);
                recyclerViewImage = dialogView.findViewById(R.id.recyclerViewImage);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                /*Glide.with(view.getRootView().getContext())
                        .load(picture)
                        .into(recyclerViewImage);*/
                recyclerViewImage.setImageURI(imageURI);

            }
        });

        coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.image_view_controller,null);
                recyclerViewImage = dialogView.findViewById(R.id.recyclerViewImage);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                Glide.with(view.getRootView().getContext())
                        .load(picture)
                        .into(recyclerViewImage);

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demoText1.setVisibility(View.VISIBLE);

                Bitmap resultBitmap = getBitmapFromView(shareLayout);

                try {
                    String root = (getActivity().getExternalCacheDir().toString());
                    File myDir = new File(root,separator + "/image");
                    myDir.mkdirs();
                    String fname = getCurrentTimeString() + ".jpg";
                    File file = new File(myDir, fname);

                    FileOutputStream out = new FileOutputStream(file);
                    /*Bitmap bm = textureView.getBitmap();*/
                    resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    createShareDialog(view,resultBitmap);

                    out.flush();
                    out.close();
                    file.setReadable(true,false);



                    /*Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    startActivity(Intent.createChooser(intent,"Share by"));*/

                /*} catch (Exception e) {
                    Log.d("onBtnSavePng", e.toString());*/
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 0;
                feedbackRequest(view,result);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 1;
                feedbackRequest(view,result);
            }
        });
        doNotKnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 2;
                feedbackRequest(view,result);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void feedbackRequest(View view,int result) {

        int id = 0;

        try {
            id = Integer.parseInt(imageId);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        String BASE_URL = "https://genofax.com/api/breed/feedback/"+id+"/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/
        Service service = retrofit.create(Service.class);
        Call<JSONObject> call = service.postFeedBack(result);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful()){
                    new iOSDialogBuilder(getContext())
                            .setTitle("Thank you")
                            .setSubtitle("We have received your feedback")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                   /* HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();*/
                                    dialog.dismiss();

                                }
                            })
                            .build().show();
                }else{
                    Snackbar.make(view,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Snackbar.make(view,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initialise(View view) {
        mFBanalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "BreedResultScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "BreedResultScreen");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        progressBar = view.findViewById(R.id.progressBar);
        coverImage = view.findViewById(R.id.coverImage);
        progressBarImage = view.findViewById(R.id.progressBarImage);
        shareLayout = view.findViewById(R.id.shareLayout);
        demoText1 = view.findViewById(R.id.demoText1);


        breedNameTxt = view.findViewById(R.id.breedNameTxt);
        percentageShowTxt = view.findViewById(R.id.percentageShowTxt);


        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        doNotKnowButton = view.findViewById(R.id.doNotKnowButton);
        shareButton = view.findViewById(R.id.shareButton);
        returnButton = view.findViewById(R.id.returnButton);


        pieChart = view.findViewById(R.id.activity_main_piechart);
        toolbarId = view.findViewById(R.id.toolbarId);

        /////recyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);

        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }

    private void showAdapter(ArrayList<DataClass> listResult) {
        if (!listResult.isEmpty()){
            adapterSetOnClickListener(listResult);
            DogBreedResultAdapter dogBreedResultAdapter = new DogBreedResultAdapter(listResult,getContext(),adapterListener);
            recyclerView.setAdapter(dogBreedResultAdapter);
        }
    }

    private void adapterSetOnClickListener(final ArrayList<DataClass> listResult) {
        adapterListener = new DogBreedResultAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                breedName = listResult.get(position).getBreed().toString();
                thumbnail = listResult.get(position).getThumbnail();
                additional_info = listResult.get(position).isAdditional_info();
                country_of_origin = listResult.get(position).getCountry_of_origin();
                brief_history = listResult.get(position).getBrief_history();
                life_span = listResult.get(position).getLife_span();
                ideal_weight = listResult.get(position).getIdeal_weight();
                ideal_height = listResult.get(position).getIdeal_height();
                temperament = listResult.get(position).getTemperament();
                utilization = listResult.get(position).getUtilization();

                createPopUpDialog();
            }
        };
    }

    public class CustomPercentFormatter extends ValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public CustomPercentFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        public CustomPercentFormatter(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value == 0.0f)
                return "";
            return mFormat.format(value) + " %";
        }
    }

    private void showChart(ArrayList<DataClass> listResult, int range) {
        ArrayList<PieEntry> list = new ArrayList<>();

        for (int i = 0; i < listResult.size(); i++) {

            float floatValue= Float.parseFloat(listResult.get(i).getPercentage());

            if (listResult.size()==1){
                float newValue = 100f;
                if (floatValue<=newValue){
                    float value = newValue-floatValue;
                    float val= (float) ((Math.random()*range)+range/ listResult.size());
                    list.add(new PieEntry(floatValue, listResult.get(i).getBreed()));
                    list.add(new PieEntry(value, ""));

                    for(int count = 0; count < listResult.size(); count++){

                       int total = (int)(Math.round(Float.parseFloat(listResult.get(count).getPercentage())));

                        picture = String.valueOf(listResult.get(0).getThumbnail());

                        String breedName = listResult.get(0).getBreed();
                        String percentage = listResult.get(0).getPercentage();

                        breedNameTxt.setText(breedName);
                        if (!classification.isEmpty()){
                            percentageShowTxt.setText(Html.fromHtml(classification));
                        }else{
                            Log.e("","null");
                        }



                        /*Glide.with(this)
                                .load(picture)
                                .into(progressBarImage);*/
                        progressBarImage.setImageURI(imageURI);


                        Glide.with(this)
                                .load(picture)
                                .into(coverImage);

                        HistoryDemoModel historyDemoModel = new HistoryDemoModel(imageId);
                        dbHelper.saveNewPerson(historyDemoModel);

                       /* Glide.with(this)
                                .load(picture)
                                .into(outputImage);
                        outputImageName.setText(breedPopUpName);
                        originName.setText(PopUpOrigin);
                        lifeSpanShow.setText(PopUpLifeSpan);
*/
                        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, currentProgress+total);
                        progressAnimator.setDuration(1000);
                        progressAnimator.setInterpolator(new LinearInterpolator());
                        progressAnimator.start();
                    }

                }

            }else{
                ArrayList<Integer> arr = new ArrayList<>();

                for(int count = 0; count < listResult.size(); count++){
                   /* total = Integer.parseInt(total + listResult.get(count).getPercentage());*/
                   int total = (int)(Math.round(Float.parseFloat(listResult.get(count).getPercentage())));
                    arr.add(total);
                }

                list.add(new PieEntry(floatValue, listResult.get(i).getBreed()));

                picture = String.valueOf(listResult.get(0).getThumbnail());

                Integer max = Collections.max(arr);

                String breedName = listResult.get(0).getBreed();
                String percentage = listResult.get(0).getPercentage();

                breedNameTxt.setText(breedName);
                percentageShowTxt.setText(Html.fromHtml(classification));


                /*Glide.with(this)
                        .load(picture)
                        .into(progressBarImage);*/

                progressBarImage.setImageURI(imageURI);

                Glide.with(this)
                        .load(picture)
                        .into(coverImage);


                HistoryDemoModel historyDemoModel = new HistoryDemoModel(imageId);
                dbHelper.saveNewPerson(historyDemoModel);

                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, max);
                progressAnimator.setDuration(1000);
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();

            }


        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        /*
        for (int color: ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }*/

        PieDataSet dataSet = new PieDataSet(list, "");
        dataSet.setColors(colors);

        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(20f);


        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        data.setValueFormatter(new CustomPercentFormatter());
        data.getYValueSum();

        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);

        pieChart.setDrawSliceText(false); // To remove slice text
        pieChart.setDrawMarkers(false); // To remove markers when click
        pieChart.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart.getDescription().setEnabled(false);
        pieChart.getData().getYValueSum();
    }


    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(Color.BLACK);
        /*pieChart.setCenterText("Classify Dog Breed");


        pieChart.setCenterTextSize(20);*/
        pieChart.getDescription().setEnabled(false);

        /*Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);*/
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
        breedNameShow.setText(breedName);
        historyShow.setText(brief_history);
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

    public String getCurrentTimeString() {
        int yyyy = Calendar.getInstance().get(Calendar.YEAR);
        int MM = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hh = Calendar.getInstance().get(Calendar.HOUR);
        int mm = Calendar.getInstance().get(Calendar.MINUTE);
        int ss = Calendar.getInstance().get(Calendar.SECOND);

        String result = yyyy+"-"+MM+"-"+dd+" "+hh+":"+mm+":"+ss;
        return result;
    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view){
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable !=null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private void newMainLayout(View view) {
        Bitmap bitmap = getBitmapFromView(mainShareLayout);

        try {
            String root = (getActivity().getExternalCacheDir().toString());
            File myDir = new File(root,separator + "/image");
            myDir.mkdirs();
            String fname = getCurrentTimeString() + ".jpg";
            File file = new File(myDir, fname);

            FileOutputStream out = new FileOutputStream(file);
            /*Bitmap bm = textureView.getBitmap();*/
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            /*chartImage.setImageBitmap(bitmap);*/

            out.flush();
            out.close();
            file.setReadable(true,false);
            Uri uri = FileProvider.getUriForFile(getActivity(), "com.teleaus.bioscience.fileprovider", file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            /*startActivity(Intent.createChooser(intent,"Share by"));*/

            Intent chooser = Intent.createChooser(intent, "Share File");

            List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            getActivity().startActivity(chooser);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createShareDialog(View view, Bitmap resultBitmap) {
        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.share_screen_view,null);

        yourImage = popUp_card.findViewById(R.id.yourImage);
        chartImage = popUp_card.findViewById(R.id.chartImage);
        outputImage = popUp_card.findViewById(R.id.outputImage);
        outputImageName = popUp_card.findViewById(R.id.outputImageName);
        originName = popUp_card.findViewById(R.id.originName);
        lifeSpanShow = popUp_card.findViewById(R.id.lifeSpanShow);
        mainShareLayout = popUp_card.findViewById(R.id.mainShareLayout);
        shareDialogButton = popUp_card.findViewById(R.id.shareDialogButton);
        returnDialogButton = popUp_card.findViewById(R.id.returnDialogButton);


       /* Glide.with(this)
                .load(image_url)
                .into(yourImage);

        Glide.with(this)
                .load(picture)
                .into(outputImage);
        outputImageName.setText(breedNameShow);
        originName.setText(origin);
        lifeSpanShow.setText(lifeSpanString);*/

        chartImage.setImageBitmap(resultBitmap);

        Glide.with(this)
                .load(picture)
                .into(outputImage);

        yourImage.setImageURI(imageURI);

        /*Glide.with(this)
                .load(image_url)
                .into(yourImage);*/

        breedPopUpName = listResult.get(0).getBreed();
        PopUpLifeSpan = listResult.get(0).getLife_span();
        PopUpOrigin = listResult.get(0).getCountry_of_origin();


        outputImageName.setText(breedPopUpName);
        /*originName.setText(PopUpLifeSpan);*/

        StringBuilder lifeSpanNamebuilder = new StringBuilder();
        for (String details : PopUpLifeSpan) {
            lifeSpanNamebuilder.append(details + ".");
        }
        lifeSpanShow.setText(lifeSpanNamebuilder.toString());

        originName.setText(PopUpOrigin);

        shareDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("ShareButtonClick","ShareButtonClick");
                mFBanalytics.logEvent("ShareButtonClick",bundle);
                newMainLayout(view);
            }
        });
        returnDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(true);
        dialog = dialogBuilder.create();
        dialog.show();

        /*       breedName,thumbnail,country_of_origin,brief_history,life_span,ideal_weight,ideal_height,temperament;*/

        /*Glide.with(this)
                .load(thumbnail)
                .into(imageUrlShow);
        historyShow.setText(brief_history);
        countryName.setText(country_of_origin);
        lifeSpan.setText(life_span);
        weightName.setText(ideal_weight);
        heightName.setText(ideal_height);
        *//*temperamentName.setText(temperament);*//*

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
    }

}