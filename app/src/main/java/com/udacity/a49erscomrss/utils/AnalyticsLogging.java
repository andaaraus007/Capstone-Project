package com.udacity.a49erscomrss.utils;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsLogging {
    private static final String EVENT_DETAIL_ENTRY = "detail_entry";
    private static final String EVENT_FAB_ACTION = "fab_action";
    private static final String EVENT_BACK_BUTTON = "back_button_pressed";
    private static final String LOG_PARAM_DETAIL_REFERRER = "detail_referrer";
    private static final String LOG_PARAM_FAB_ACTION = "fab_action";
    private static final String FAB_ACTION_SAVED = "saved";
    private static final String FAB_ACTION_REMOVED = "removed";
    private static final String BACK_BUTTON_ACTION = "back_button";
    private static final String BACK_BUTTON_TO_FAVORITE_TAB = "open_favorites_tab";
    public static final String REFERRER_FROM_WIDGET = "from widget";

    private final FirebaseAnalytics mFirebaseAnalytics;

    public AnalyticsLogging(FirebaseAnalytics firebaseAnalytics) {
        mFirebaseAnalytics = firebaseAnalytics;
    }

    public  void logDetailActivityEntry(String referrer) {
        Bundle params = new Bundle();
        params.putString(LOG_PARAM_DETAIL_REFERRER, referrer);
        mFirebaseAnalytics.logEvent(EVENT_DETAIL_ENTRY, params);
    }

    public  void logSavedArticle() {
        Bundle params = new Bundle();
        params.putString(LOG_PARAM_FAB_ACTION, FAB_ACTION_SAVED);
        mFirebaseAnalytics.logEvent(EVENT_FAB_ACTION, params);
    }

    public  void logRemovedArticle() {
        Bundle params = new Bundle();
        params.putString(LOG_PARAM_FAB_ACTION, FAB_ACTION_REMOVED);
        mFirebaseAnalytics.logEvent(EVENT_FAB_ACTION, params);
    }

    public  void logOpenFavoriteTabFromDetail() {
        Bundle params = new Bundle();
        params.putString(BACK_BUTTON_ACTION, BACK_BUTTON_TO_FAVORITE_TAB);
        mFirebaseAnalytics.logEvent(EVENT_BACK_BUTTON, params);
    }
}
