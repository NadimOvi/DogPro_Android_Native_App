package com.teleaus.bioscience.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toolbar;

import com.teleaus.bioscience.R;


public class ContactUsFragment extends Fragment {
    Toolbar toolbarId;
    private WebView webView;
    private ProgressDialog progressDialog;

    public static final String Login_Post_En_URL = "https://genofax.com/contact";
    public static final String Login_Post_Bn_URL = "https://genofax.com/contact";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);

        initialise(view);
        return view;
    }

    private void initialise(View view) {
        toolbarId = view.findViewById(R.id.toolbarId);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        webView = view.findViewById(R.id.webView);
        webView.requestFocus();
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.flush();


        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getActivity().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setDomStorageEnabled(true);
        /*webView.getSettings().setBlockNetworkLoads(true);*/
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
        /*   webView.getSettings().setSavePassword(true);*/
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setEnableSmoothTransition(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }

        });


        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.alert_network_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button btTryAgain = dialog.findViewById(R.id.bt_try_again);

            btTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().recreate();
                }
            });
            dialog.show();
        } else {

            //URL gir "....."
            getWebview("https://genofax.com/contact");
        }

    }

    protected boolean loginURL_BN_Matching(String url) {
        return url.toLowerCase().contains(Login_Post_Bn_URL.toLowerCase());
    }
    protected boolean loginURL_EN_Matching(String url) {
        return url.toLowerCase().contains(Login_Post_En_URL.toLowerCase());
    }
    public void getWebview(String myurl) {
        progressDialog = new ProgressDialog(getActivity());


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                view.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAppCachePath(getActivity().getFilesDir().getAbsolutePath() + "/cache");
                webView.getSettings().setDatabasePath(getActivity().getFilesDir().getAbsolutePath() + "/databases");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                        view.loadData("<html>SOMETHING WENT WRONG!,Please Check your Internet Connection</html>", "", "");
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    public void onPageFinished(WebView view, String url) {
                        CookieSyncManager.getInstance().sync();
                    }

                });


                CookieSyncManager.createInstance(webView.getContext());
                CookieSyncManager.getInstance().sync();
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (loginURL_BN_Matching(url)) {
                    /*try {
                        postInfo();
                    } catch (Exception e) {
                        Log.e("Fail 2", e.toString());
                        //At the level Exception Class handle the error in Exception Table
                        // Exception Create That Error  Object and throw it
                        //E.g: FileNotFoundException ,etc
                        e.printStackTrace();
                    }*/
                }
                super.onPageStarted(view, url, favicon);
            }
        });


        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        webView.loadUrl(myurl);

    }

    @Override
    public void onResume() {
        super.onResume();
    }



}