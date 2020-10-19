package com.udacity.a49erscomrss.ui.main;

import com.udacity.a49erscomrss.model.RssFeed;
import com.udacity.a49erscomrss.utils.RetrofitApiClient;
import com.udacity.a49erscomrss.utils.RssService;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 */
public class VideoFragment extends CoreLogicFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VideoFragment newInstance() {

        return new VideoFragment();
    }

    @Override
    public void setTabLabel() {
        mTabLabel = SectionsPagerAdapter.TAB_INDEX_VIDEO;
    }

    @Override
    public Call<RssFeed> getRssItems() {
        RssService rssService = RetrofitApiClient.getClient().create(RssService.class);
        return rssService.getVideoItems();
    }
}