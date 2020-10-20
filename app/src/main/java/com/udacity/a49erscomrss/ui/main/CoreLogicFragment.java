package com.udacity.a49erscomrss.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.udacity.a49erscomrss.activity.DetailedActivity;
import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.database.RssItemEntry;
import com.udacity.a49erscomrss.model.RssChannel;
import com.udacity.a49erscomrss.model.RssFeed;
import com.udacity.a49erscomrss.model.RssItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public abstract class CoreLogicFragment extends Fragment implements RssItemAdapter.ListItemClickListener{
    private static final int PORTRAIT_COLUMNS = 1;
    private static final int LANDSCAPE_COLUMNS = 2;
    private static final int TABLET_PORTRAIT_COLUMNS = 2;
    private static final int TABLET_LANDSCAPE_COLUMNS = 4;

    protected int mTabLabel = SectionsPagerAdapter.TAB_INDEX_NEWS;
    protected RssItemAdapter mAdapter;
    protected List<RssItem> mRssItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        Context context = view.getContext();
        LinearLayout tableLayout = view.findViewById(R.id.ll_tablet);
        RecyclerView mRecyclerView = view.findViewById(R.id.list);
        int mNumberOfColumns;
        if (tableLayout != null) {
            mNumberOfColumns = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                    TABLET_PORTRAIT_COLUMNS :
                    TABLET_LANDSCAPE_COLUMNS;
        } else {
            mNumberOfColumns = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                    PORTRAIT_COLUMNS :
                    LANDSCAPE_COLUMNS;
        }
        // Set the adapter
        if (mRecyclerView != null) {
            StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(mNumberOfColumns, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
            List<RssItem> rssItemList = new ArrayList<>();
            mAdapter = new RssItemAdapter(rssItemList, this);
            mRecyclerView.setAdapter(mAdapter);

            loadRssData();
        }
        return view;
    }

    protected void loadRssData() {
        Call<RssFeed> newsFeed = getRssItems();
        newsFeed.enqueue(new Callback<RssFeed>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        RssChannel rssChannel = response.body().getChannel();
                        if (rssChannel != null) {
                            mRssItemList = rssChannel.getItem();
                            if (mRssItemList != null) {
                                mAdapter.addRssItemData(mRssItemList);
                                setSavedStatus();
                            }
                        }
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<RssFeed> call, Throwable t) {
            }
        });
    }

    private void setSavedStatus() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getRssItems().observe(this, rssItemEntries -> {
            List<String> savedRssItemGuids = new ArrayList<>();
            for (int i = 0; i < rssItemEntries.size(); i++) {
                RssItemEntry rssItemEntry = rssItemEntries.get(i);
                savedRssItemGuids.add(rssItemEntry.getGuid());
            }
            replaceRssItemList(savedRssItemGuids);
        });
    }

    private void replaceRssItemList(List<String> savedRssItemGuids) {
        for (int i = 0; i < mRssItemList.size(); i++) {
            if (savedRssItemGuids.contains(mRssItemList.get(i).getGuid())) {
                mRssItemList.get(i).setSaved(true);
            } else {
                mRssItemList.get(i).setSaved(false);
            }
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Context context = getContext();
        Class<DetailedActivity> destinationActivity = DetailedActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        RssItem rssItem = mRssItemList.get(clickedItemIndex);
        startChildActivityIntent.putExtra(String.valueOf(R.string.title_activity_detailed), rssItem);
        startChildActivityIntent.putExtra(String.valueOf(R.string.rss_feed_type), mTabLabel);

        startActivity(startChildActivityIntent);
    }

    public abstract void setTabLabel();

    public abstract Call<RssFeed> getRssItems();
}