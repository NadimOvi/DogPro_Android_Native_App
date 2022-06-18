package com.teleaus.bioscience;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.basusingh.beautifulprogressdialog.BeautifulProgressDialog;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.teleaus.bioscience.API.Service;
import com.teleaus.bioscience.Fragments.BreedListFragment;
import com.teleaus.bioscience.Fragments.FilterFragment;
import com.teleaus.bioscience.Fragments.HistoryFragment;
import com.teleaus.bioscience.Fragments.HomeFragment;
import com.teleaus.bioscience.Fragments.ResultFragment;

import com.teleaus.bioscience.Model.BreedHistoryData;
import com.teleaus.bioscience.Model.BreedHistoryMainModel;
import com.teleaus.bioscience.Model.BreedMainModelList;
import com.teleaus.bioscience.Model.DataClass;
import com.teleaus.bioscience.Model.DataList;
import com.teleaus.bioscience.Model.MainModelClass;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity /*implements InAppUpdateManager.InAppUpdateHandler*/ {

    ////Google Analytics
    Tracker mTracker;
    private FirebaseAnalytics mFBanalytics;

    ////Camera View
    LinearLayout cameraButton, galleryButton;
    Button cancelButton;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    ////

    ///// Camera View
    public final String APP_TAG = "crop";
    public String intermediateName = "1.jpg";
    public String resultName = "2.jpg";
    Uri intermediateProvider;
    Uri resultProvider;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    ActivityResultLauncher<Intent> cropActivityResultLauncher;
    Bitmap cropImage;

    //////

    private ProgressDialog progressBar;
    BeautifulProgressDialog progressDialog;



    String currentPhotoPath;
    String currentPhotoPath2;
    private String cameraPermission[];
    private  static final int CAMERA_REQUEST_CODE = 200;
    Uri resultUri;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    ArrayList<DataList> dataLists;
    ArrayList<BreedHistoryData> breedHistoryLists;

    String BASE_URL = "https://api.genofax.com/";
    String BASE_HISTORY_URL ="https://genofax.com/api/breed/";
    String token = "FE:38:E1:DA:42:34:BB:83:F3:1C:B8:27:75:9F:75:80:98:A8:24:93";

    ////play-store

    /*private static final int REQ_CODE_VERSION_UPDATE = 530;
    private static final String TAG = "Sample";
    private InAppUpdateManager inAppUpdateManager;*/

    ////////
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mFBanalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mFBanalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        /////
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        //////


        progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setMessage("Please Wait...");
        progressBar.setCancelable(false);


        /*progressDialog = new BeautifulProgressDialog(MainActivity.this, BeautifulProgressDialog.withGIF, "Please wait");
        Uri myUri = Uri.fromFile(new File("//android_asset/sample_gif_1.gif"));
        progressDialog.setGifLocation(myUri);
        progressDialog.setLayoutColor(getResources().getColor(R.color.transparentcolor));*/

        progressDialog = new BeautifulProgressDialog(MainActivity.this, BeautifulProgressDialog.withLottie, null);
        progressDialog.setLottieLocation("lottie_1.json");
//Loop the Lottie Animation
        progressDialog.setLottieLoop(true);
        
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        /*fab.setBackgroundColor(Color.parseColor("#FFFFFF"));*/
       /* fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));*/
        float radius = getResources().getDimension(R.dimen.space_item_icon_default_size);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomappbar);

        /////////////////Breed List and History
        if (isConnected()){
            breedList();
            breedHistory();

        }else{
            new iOSDialogBuilder(MainActivity.this)
                    .setTitle("Alert!")
                    .setSubtitle("No Internet Connection from your device")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                            recreate();

                        }
                    })
                    .build().show();
        }


        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(
                bottomBarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
                        .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                        .build());

        cameraPermission= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Bundle bundle = new Bundle();
                Bundle params = new Bundle();
                Bundle newbundle = new Bundle();
                params.putInt("ButtonID", item.getItemId());
                switch (item.getItemId())
                {
                    case R.id.home :
                        selectedFragment = new HomeFragment();

                        newbundle.putString("HomeScreenClick","HomeScreenClick");
                        mFBanalytics.logEvent("HomeScreenClick",newbundle);

                        bundle.putParcelableArrayList("breedHistoryLists", breedHistoryLists);
                        selectedFragment.setArguments(bundle);
                        break;
                    case R.id.history: selectedFragment = new HistoryFragment();

                        newbundle.putString("HistoryScreenClick","HistoryScreenClick");
                        mFBanalytics.logEvent("HistoryScreenClick",newbundle);

                        bundle.putParcelableArrayList("breedHistoryLists", breedHistoryLists);
                        selectedFragment.setArguments(bundle);
                        break;
                    case R.id.list: selectedFragment = new BreedListFragment();

                        newbundle.putString("BreedListScreenClick","BreedListScreenClick");
                        mFBanalytics.logEvent("BreedListScreenClick",newbundle);

                        bundle.putParcelableArrayList("dataLists", dataLists);
                        selectedFragment.setArguments(bundle);
                        break;
                    /*case R.id.profile:

                        selectedFragment = new LoginFragment();
                        break;*/
                    case R.id.filter:
                        newbundle.putString("FilterScreenClick","FilterScreenClick");
                        mFBanalytics.logEvent("FilterScreenClick",newbundle);
                        selectedFragment = new FilterFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {

                    if (!checkCameraPermission()){

                        requestCameraPermission();

                    }else{
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                            // Do something for lollipop and above versions
                            createPopUpDialog();
                        } else{
                            // do something for phones running an SDK before lollipop
                            showImageImportDialog();
                            /*createPopUpDialog();*/
                        }

                    }

                  /*
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        // Do something for lollipop and above versions
                        createPopUpDialog();
                    } else{
                        // do something for phones running an SDK before lollipop
                        showImageImportDialog();
                    }*/
                }else{
                    new iOSDialogBuilder(MainActivity.this)
                            .setTitle("Alert!")
                            .setSubtitle("No Internet Connected from your device")
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
            }
        });

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap takenImage = MainActivity.this.loadFromUri(intermediateProvider);

                            MainActivity.this.onCropImage();
                        }
                    }
                });

        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            MainActivity.this.saveBitmapFileToIntermediate(result.getData().getData());
                            Bitmap selectedImage = MainActivity.this.loadFromUri(intermediateProvider);

                            MainActivity.this.onCropImage();
                        }
                    }
                });

        cropActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            cropImage = MainActivity.this.loadFromUri(resultProvider);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            cropImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);



                            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),cropImage,"val",null);
                            Uri uri = Uri.parse(path);
                            Context context = MainActivity.this;
                            String realPath = RealPathUtil.getRealPath(context,uri);

                            /*File photoNewfile = new File(uri.getPath());

                            Uri photoUri = Uri.fromFile(photoNewfile);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                photoUri = FileProvider.getUriForFile(MainActivity.this,"com.teleaus.bioscience.fileprovider", photoNewfile);
                            }*/

                            File file = new File (realPath);

                            File newFile = saveBitmapToFile(file);

                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), newFile);
                            MultipartBody.Part part = MultipartBody.Part.createFormData("file",newFile.getName(),requestBody);

                            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                                    .connectTimeout(10, TimeUnit.SECONDS)
                                    .readTimeout(10, TimeUnit.SECONDS)
                                    .writeTimeout(10, TimeUnit.SECONDS)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .client(okHttpClient)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();


                            Service service = retrofit.create(Service.class);

                            progressDialog.show();
                            Call<MainModelClass> call = service.postImage(token,part);
                            call.enqueue(new Callback<MainModelClass>() {
                                @Override
                                public void onResponse(Call<MainModelClass> call, Response<MainModelClass> response) {

                                    if(response.isSuccessful()){

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        }, 2000);

                                        MainModelClass mainModelClass = response.body();
                                        String imageId = mainModelClass.getId();
                                        String imageUrl = mainModelClass.getImage_url();
                                        String classification = mainModelClass.getResponse().getClassification_summary();
                                        ArrayList<DataClass> dataClassList = response.body().getResponse().getData();

                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("dataClassList", dataClassList);
                                        bundle.putParcelable("resultUri", resultProvider);
                                        bundle.putString("imageId",imageId);
                                        bundle.putString("imageUrl",imageUrl);
                                        bundle.putString("classification",classification);

                                        ResultFragment resultFragment = new ResultFragment();
                                        resultFragment.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultFragment).commit();

                                    }else{

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        }, 2000);

                                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MainModelClass> call, Throwable t) {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);

                                    Toast.makeText(MainActivity.this, "Sorry we could not classify this image into a breed. See the list of breeds we support.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

    }

    /*@Override
    public void onInAppUpdateError(int code, Throwable error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {

         // If the update downloaded, ask user confirmation and complete the update


        if (status.isDownloaded()) {

            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar.make(rootView,
                    "An update has just been downloaded.",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("RESTART", view -> {

                // Triggers the completion of the update of the app for the flexible flow.
                inAppUpdateManager.completeUpdate();

            });

            snackbar.show();

        }*//*else{
            if (isConnected()){
                breedList();
                breedHistory();

            }else{
                new iOSDialogBuilder(MainActivity.this)
                        .setTitle("Alert!")
                        .setSubtitle("No Internet Connection from your device")
                        .setBoldPositiveLabel(true)
                        .setCancelable(false)
                        .setPositiveListener("Ok",new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                                recreate();

                            }
                        })
                        .build().show();
            }
        }*//*
    }*/


    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void showImageImportDialog() {
        if (!checkCameraPermission()){

            requestCameraPermission();

        }else{

            pickCamera();

        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);

    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*pickCamera();*/
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    // Do something for lollipop and above versions
                    createPopUpDialog();
                } else{
                    // do something for phones running an SDK before lollipop
                    showImageImportDialog();
                }

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the Camera permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pickCamera() {

        /*imageNo=0;*/
        getImageClick();
    }

    public void getImageClick() {
        CropImage.startPickImageActivity(this);
    }

    //this for Crope Image
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if(requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }else */if (requestCode == RC_APP_UPDATE && resultCode !=RESULT_OK){
            Toast.makeText(this, "Update Cancel", Toast.LENGTH_SHORT).show();
        }else if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            Bundle bundle = new Bundle();
            bundle.putString("Gallery_Image","Gallery_Image");
            mFBanalytics.logEvent("Gallery_Image",bundle);
            startCropImageActivity(imageUri);
        }

        // handle result of CropImageActivity
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resultUri = result.getUri();

            Bundle bundle = new Bundle();
            bundle.putString("Capture_Image","Capture_Image");
            mFBanalytics.logEvent("Capture_Image",bundle);

            File photoNewfile = new File(resultUri.getPath());
            currentPhotoPath2 = photoNewfile.getPath();


            File file = new File (currentPhotoPath2);

            File newFile = saveBitmapToFile(file);



            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), newFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",newFile.getName(),requestBody);

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            Service service = retrofit.create(Service.class);

            progressDialog.show();

            Call<MainModelClass> call = service.postImage(token,part);
            call.enqueue(new Callback<MainModelClass>() {
                @Override
                public void onResponse(Call<MainModelClass> call, Response<MainModelClass> response) {



                    if(response.isSuccessful()){
                        MainModelClass mainModelClass = response.body();
                        String imageId = mainModelClass.getId();
                        String imageUrl = mainModelClass.getImage_url();
                        String classification = mainModelClass.getResponse().getClassification_summary();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 2000);


                        ArrayList<DataClass> dataClassList = response.body().getResponse().getData();

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("dataClassList", dataClassList);
                        bundle.putParcelable("resultUri", resultUri);
                        bundle.putString("classification",classification);
                        bundle.putString("imageId",imageId);
                        bundle.putString("imageUrl",imageUrl);


                        ResultFragment resultFragment = new ResultFragment();
                        resultFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultFragment).commit();

                    }else if (resultCode == 404){

                        Toast.makeText(MainActivity.this, "Sorry we could not classify this image into a breed. See the list of breeds we support. ", Toast.LENGTH_SHORT).show();
                    } else{

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 2000);

                        Toast.makeText(MainActivity.this,"Sorry we could not classify this image into a breed. See the list of breeds we support.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MainModelClass> call, Throwable t) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 2000);

                    Toast.makeText(MainActivity.this, "Sorry we could not classify this image into a breed. See the list of breeds we support. ", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void breedList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/
        Service service = retrofit.create(Service.class);

        progressDialog.show();
        Call<BreedMainModelList> call = service.postBreedList(token);
        call.enqueue(new Callback<BreedMainModelList>() {
            @Override
            public void onResponse(Call<BreedMainModelList> call, Response<BreedMainModelList> response) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);

                if (response.isSuccessful()){
                    dataLists = response.body().getData();

                } else{
                    /*Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();*/
                    Log.e("Error","Not found");
                }
            }

            @Override
            public void onFailure(Call<BreedMainModelList> call, Throwable t) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void breedHistory(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_HISTORY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Service service = Client.retrofit.create(Service.class);*/
        Service service = retrofit.create(Service.class);
        Call<BreedHistoryMainModel> call = service.breedHistory();

        call.enqueue(new Callback<BreedHistoryMainModel>() {
            @Override
            public void onResponse(Call<BreedHistoryMainModel> call, Response<BreedHistoryMainModel> response) {
                if (response.isSuccessful()){
                    breedHistoryLists = response.body().getData();


                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("breedHistoryLists", breedHistoryLists);
                    homeFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BreedHistoryMainModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(this);

        final View popUp_card = getLayoutInflater().inflate(R.layout.camera_choice_view,null);

        cameraButton = popUp_card.findViewById(R.id.cameraButton);
        galleryButton = popUp_card.findViewById(R.id.galleryButton);
        cancelButton = popUp_card.findViewById(R.id.cancelButton);

        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Capture_Image","Capture_Image");
                mFBanalytics.logEvent("Capture_Image",bundle);
                onLaunchCamera();
                dialog.dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Gallery_Image","Gallery_Image");
                mFBanalytics.logEvent("Gallery_Image",bundle);
                onPickPhoto();
                dialog.dismiss();
            }
        });





    }
    /// camera capture
    public void onLaunchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = getPhotoFileUri(intermediateName);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intermediateProvider = FileProvider.getUriForFile(MainActivity.this, "com.teleaus.bioscience.fileprovider", photoFile);

        else
            intermediateProvider = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, intermediateProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            cameraActivityResultLauncher.launch(intent);
        }
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            galleryActivityResultLauncher.launch(intent);
        }
    }

    private void onCropImage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            grantUriPermission("com.android.camera", intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(intermediateProvider, "image/*");

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

            int size = 0;

            if(list != null) {
                grantUriPermission(list.get(0).activityInfo.packageName, intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                size = list.size();
            }

            if (size == 0) {
                Toast.makeText(this, "Error, wasn't taken image!", Toast.LENGTH_SHORT).show();
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra("crop", "true");

                intent.putExtra("scale", true);

                File photoFile = getPhotoFileUri(resultName);
                // wrap File object into a content provider
                // required for API >= 24
                // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                resultProvider = FileProvider.getUriForFile(MainActivity.this, "com.teleaus.bioscience.fileprovider", photoFile);

                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

                Intent cropIntent = new Intent(intent);
                ResolveInfo res = list.get(0);
                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                grantUriPermission(res.activityInfo.packageName, resultProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
               /* Toast.makeText(this, "1st Crop", Toast.LENGTH_SHORT).show();*/
                cropActivityResultLauncher.launch(cropIntent);

            }
        } else {
            File photoFile = getPhotoFileUri(resultName);
            resultProvider = Uri.fromFile(photoFile);

            Intent intentCrop = new Intent("com.android.camera.action.CROP");
            intentCrop.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentCrop.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentCrop.setDataAndType(intermediateProvider, "image/*");
            intentCrop.putExtra("crop", "true");
            intentCrop.putExtra("scale", true);
            intentCrop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intentCrop.putExtra("noFaceDetection", true);
            intentCrop.putExtra("return-data", false);
            intentCrop.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
            cropActivityResultLauncher.launch(intentCrop);

        }
    }

    // Returns the File for a photo stored on disk given the fileName

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(""), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void saveBitmapFileToIntermediate(Uri sourceUri) {
        try {
            Bitmap bitmap =  loadFromUri(sourceUri);

            File imageFile = getPhotoFileUri(intermediateName);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                intermediateProvider = FileProvider.getUriForFile(MainActivity.this, "com.teleaus.bioscience.fileprovider", imageFile);
            else
                intermediateProvider = Uri.fromFile(imageFile);

            OutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED){
                showCompletedUpdated();
            }
        }
    };

    private void showCompletedUpdated() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onStop() {
        if (mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTracker.setScreenName("Image~" + "Google Analytics Testing");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}