package com.udacity.a49erscomrss.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.databinding.ActivityMainBinding;
import com.udacity.a49erscomrss.ui.main.SectionsPagerAdapter;
import com.udacity.a49erscomrss.utils.AnalyticsLogging;
import com.udacity.a49erscomrss.widget.A49ersRSSWidget;

public class MainActivity extends AppCompatActivity {
    private AnalyticsLogging mAnalyticsLogging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnalyticsLogging = new AnalyticsLogging(FirebaseAnalytics.getInstance(this));
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = activityMainBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        handleBackPressed(viewPager);
        TabLayout tabs = activityMainBinding.tabs;
        tabs.setupWithViewPager(viewPager);

        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView = activityMainBinding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void handleBackPressed(ViewPager viewPager) {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(A49ersRSSWidget.EXTRA_FROM_WIDGET)) {
                viewPager.setCurrentItem(SectionsPagerAdapter.TAB_INDEX_FAVORITE);
                mAnalyticsLogging.logOpenFavoriteTabFromDetail();
            }
        }
    }
}