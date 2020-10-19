package com.udacity.a49erscomrss.ui.main;

import androidx.lifecycle.ViewModelProvider;

import com.udacity.a49erscomrss.database.RssItemEntry;
import com.udacity.a49erscomrss.model.RssFeed;
import com.udacity.a49erscomrss.model.RssItem;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 */
public class FavoriteFragment extends CoreLogicFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {

        return new FavoriteFragment();
    }

    protected void loadRssData() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getRssItems().observe(this, rssItemEntries -> {
            mRssItemList.clear();
            for (int i = 0; i < rssItemEntries.size(); i++) {
                RssItemEntry rssItemEntry = rssItemEntries.get(i);
                RssItem rssItem = new RssItem(
                        rssItemEntry.getGuid(),
                        rssItemEntry.getTitle(),
                        rssItemEntry.getDescription(),
                        rssItemEntry.getLink(),
                        true
                );
                mRssItemList.add(rssItem);
            }
            mAdapter.addRssItemData(mRssItemList);
        });
    }



    @Override
    public void setTabLabel() {
        mTabLabel = SectionsPagerAdapter.TAB_INDEX_FAVORITE;
    }

    @Override
    public Call<RssFeed> getRssItems() {
        return null;
    }
}