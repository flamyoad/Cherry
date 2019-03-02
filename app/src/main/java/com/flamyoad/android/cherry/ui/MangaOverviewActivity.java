package com.flamyoad.android.cherry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.flamyoad.android.cherry.adapter.BookListAdapter;
import com.flamyoad.android.cherry.adapter.MangaOverviewPagerAdapter;
import com.flamyoad.android.cherry.viewmodel.OverviewViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;

import com.flamyoad.android.cherry.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MangaOverviewActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mCoverImage;

    private OverviewViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModel = ViewModelProviders.of(this).get(OverviewViewModel.class);

        mCoverImage = findViewById(R.id.manga_overview_coverimg);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(BookListAdapter.BOOK_NAME);
            setTitle(title);
            mViewModel.setTitle(title);
            mViewModel.initialize(intent.getStringExtra(BookListAdapter.BOOK_URL));
            Glide.with(this)
                    .load(intent.getStringExtra(BookListAdapter.BOOK_IMG))
                    .into(mCoverImage);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mViewPager = findViewById(R.id.manga_overview_viewpager);
        mViewPager.setAdapter(new MangaOverviewPagerAdapter(getSupportFragmentManager(), this));

        mTabLayout = findViewById(R.id.manga_overview_tablayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
