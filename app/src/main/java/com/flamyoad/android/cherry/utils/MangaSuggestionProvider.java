package com.flamyoad.android.cherry.utils;

import android.content.SearchRecentSuggestionsProvider;

public class MangaSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.flamyoad.android.cherry.MangaSuggestionProvider";

    public static final int MODE = DATABASE_MODE_QUERIES;

    public MangaSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
