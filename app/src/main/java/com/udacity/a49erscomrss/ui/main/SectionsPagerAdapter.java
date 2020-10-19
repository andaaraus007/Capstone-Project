package com.udacity.a49erscomrss.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.udacity.a49erscomrss.R;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_INDEX_NEWS = 0;
    public static final int TAB_INDEX_PHOTO = 1;
    public static final int TAB_INDEX_VIDEO = 2;
    public static final int TAB_INDEX_AUDIO = 3;
    public static final int TAB_INDEX_FAVORITE = 4;

    public static ArrayList<Integer> tabTitles;

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;



        tabTitles = new ArrayList<>();
        tabTitles.add(TAB_INDEX_NEWS, R.string.tab_news);
        tabTitles.add(TAB_INDEX_PHOTO, R.string.tab_photo);
        tabTitles.add(TAB_INDEX_VIDEO, R.string.tab_video);
        tabTitles.add(TAB_INDEX_AUDIO, R.string.tab_audio);
        tabTitles.add(TAB_INDEX_FAVORITE, R.string.tab_favorite);
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case TAB_INDEX_FAVORITE :
                fragment = FavoriteFragment.newInstance();
                break;

            case TAB_INDEX_PHOTO :
                fragment = PhotoFragment.newInstance();
                break;

            case TAB_INDEX_VIDEO :
                fragment = VideoFragment.newInstance();
                break;

            case TAB_INDEX_AUDIO :
                fragment = AudioFragment.newInstance();
                break;

            default :
                fragment = NewsFragment.newInstance();
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(tabTitles.get(position));
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }
 }