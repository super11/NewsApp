package com.flipkart.newsapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.flipkart.newsapp.R;
import com.flipkart.newsapp.model.ResponseData;
import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;

/**
 * Created by ashokkumar.y on 15/05/15.
 */
public class ArticleNewsDetailAdapter  extends PagerAdapter{

    private static final String TAG = "ARTICLE";
    Context mContext;
    ResponseData mResponse;
   // ImageLoader mImageLoader;
   // NetworkRequestQueue mQueue;
    LayoutInflater mLayoutInflater;
    WebView mWebView;

   ProgressBar progressBar;
   //constructor
   public ArticleNewsDetailAdapter(Context context,ResponseData response,int position){
       this.mContext=context;
       this.mResponse=response;
      // mQueue = NetworkRequestQueue.getInstance();
      // mQueue.initialize(context);
       //mImageLoader=mQueue.getImageLoader();
       mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

   }
    @Override
    public int getCount() {
        return mResponse.getNewsData().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.article_item_view_pager, container, false);
        mWebView=(WebView)itemView.findViewById(R.id.main_webview);
      progressBar=(ProgressBar)itemView.findViewById(R.id.progressBar);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(mResponse.getNewsData().get(position).getContentUrl());
        mWebView.setWebViewClient(new WebViewClient());
        /*mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               // return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished");
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
            }
        });*/
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if(newProgress==100){
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        } );
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}