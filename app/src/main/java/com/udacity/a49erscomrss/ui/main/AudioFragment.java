package com.udacity.a49erscomrss.ui.main;

import com.udacity.a49erscomrss.model.RssFeed;
import com.udacity.a49erscomrss.utils.RetrofitApiClient;
import com.udacity.a49erscomrss.utils.RssService;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 */
public class AudioFragment extends CoreLogicFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AudioFragment() {
    }

    public static AudioFragment newInstance() {

        return new AudioFragment();
    }

    @Override
    public void setTabLabel() {
        mTabLabel = SectionsPagerAdapter.TAB_INDEX_AUDIO;
    }

    @Override
    public Call<RssFeed> getRssItems() {
        RssService rssService = RetrofitApiClient.getClient().create(RssService.class);
        return rssService.getAudioItems();
    }
}