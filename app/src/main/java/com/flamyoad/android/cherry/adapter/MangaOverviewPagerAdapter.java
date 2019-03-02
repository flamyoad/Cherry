package com.flamyoad.android.cherry.adapter;

import android.content.Context;

import com.flamyoad.android.cherry.ui.ChapterListFragment;
import com.flamyoad.android.cherry.ui.SummaryFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MangaOverviewPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private final String[] tabTitles = {"Summary", "Chapters"};

    private Context mContext;

    public MangaOverviewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SummaryFragment();
            case 1: return new ChapterListFragment();
            default: throw new RuntimeException("Out of index in MangaOverviewPagerAdapter getItem()");
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
