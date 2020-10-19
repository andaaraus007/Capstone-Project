package com.udacity.a49erscomrss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.database.AppDatabase;
import com.udacity.a49erscomrss.database.RssItemEntry;
import com.udacity.a49erscomrss.databinding.ActivityDetailedBinding;
import com.udacity.a49erscomrss.model.RssItem;
import com.udacity.a49erscomrss.ui.main.SectionsPagerAdapter;
import com.udacity.a49erscomrss.utils.AnalyticsLogging;
import com.udacity.a49erscomrss.utils.AppExecutors;
import com.udacity.a49erscomrss.widget.A49ersRSSWidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class DetailedActivity extends AppCompatActivity {
    private static final int DEFAULT_FEED_TYPE = SectionsPagerAdapter.TAB_INDEX_FAVORITE;

    private RssItem mRssItem;
    private ActivityDetailedBinding mActivityDetailedBinding;
    private AppDatabase mDatabase;
    private boolean mIsFromWidget = false;
    private AnalyticsLogging mAnalyticsLogging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnalyticsLogging = new AnalyticsLogging(FirebaseAnalytics.getInstance(this));
        mActivityDetailedBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed);
        Toolbar toolbar = mActivityDetailedBinding.toolbar;
        setSupportActionBar(toolbar);
        mDatabase = AppDatabase.getInstance(getApplicationContext());

        initView();

        FloatingActionButton fab = mActivityDetailedBinding.fab;
        fab.setOnClickListener(view -> {
            if (mRssItem.isSaved()) {
                removeArticleToFavorite();
            } else {
                addArticleToFavorite();
            }
            A49ersRSSWidget.sendRefreshBroadcast(getApplicationContext());
        });
    }

    @Override
    public void onBackPressed() {
        if (mIsFromWidget) {
            Context context = getApplicationContext();
            Class<MainActivity> destinationActivity = MainActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startChildActivityIntent.putExtra(A49ersRSSWidget.EXTRA_FROM_WIDGET, A49ersRSSWidget.EXTRA_FROM_WIDGET);

            startActivity(startChildActivityIntent);
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            int feedType = DEFAULT_FEED_TYPE;
            if (intent.hasExtra(String.valueOf(R.string.rss_feed_type))) {
                feedType = intent.getIntExtra(String.valueOf(R.string.rss_feed_type), DEFAULT_FEED_TYPE);
            }
            if (intent.hasExtra(A49ersRSSWidget.EXTRA_FROM_WIDGET)) {
                mIsFromWidget = true;
            }
            if (intent.hasExtra(String.valueOf(R.string.title_activity_detailed))) {
                mRssItem = (RssItem) intent.getSerializableExtra(String.valueOf(R.string.title_activity_detailed));
                if (mRssItem != null) {
                    String appName = getApplicationContext().getResources().getString(R.string.app_name);
                    String activityTitle = appName;
                    if (feedType != DEFAULT_FEED_TYPE) {
                        activityTitle = String.format(
                                getApplicationContext().getResources().getString(R.string.app_detailed_activity_title_format),
                                appName,
                                getApplicationContext().getResources().getString(SectionsPagerAdapter.tabTitles.get(feedType)));
                    }

                    setTitle(activityTitle);
                    String url = mRssItem.getLink();
                    WebView webView = mActivityDetailedBinding.wvDisplay;
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webView.loadUrl(url);
                    setFabImage(mRssItem.isSaved());

                    String referrer = (mIsFromWidget) ?
                            AnalyticsLogging.REFERRER_FROM_WIDGET :
                            getApplicationContext().getResources().getString(SectionsPagerAdapter.tabTitles.get(feedType));
                    mAnalyticsLogging.logDetailActivityEntry(referrer);
                }
            }
        }
    }

    private void addArticleToFavorite() {
        final RssItemEntry rssItemEntry = new RssItemEntry(
                mRssItem.getGuid(),
                mRssItem.getTitle(),
                mRssItem.getDescription(),
                mRssItem.getLink()
        );
        mRssItem.setSaved(true);
        AppExecutors.getInstance().diskIO().execute(() -> {
            mDatabase.rssItemDao().insertRssItem(rssItemEntry);
            runOnUiThread(() -> setFabImage(mRssItem.isSaved()));
        });

        mAnalyticsLogging.logSavedArticle();
    }

    private void removeArticleToFavorite() {
        if (mRssItem.isSaved()) {
            final RssItemEntry rssItemEntry = new RssItemEntry(
                    mRssItem.getGuid(),
                    mRssItem.getTitle(),
                    mRssItem.getDescription(),
                    mRssItem.getLink()
            );
            mRssItem.setSaved(false);
            AppExecutors.getInstance().diskIO().execute(() -> {
                mDatabase.rssItemDao().deleteRssItem(rssItemEntry);
                runOnUiThread(() -> setFabImage(mRssItem.isSaved()));
            });
        }

        mAnalyticsLogging.logRemovedArticle();
    }

    private void setFabImage(boolean isSaved) {
        if (isSaved) {
            mActivityDetailedBinding.fab.setImageResource(R.drawable.white_x);
        } else {
            mActivityDetailedBinding.fab.setImageResource(R.drawable.white_plus);
        }
    }
}