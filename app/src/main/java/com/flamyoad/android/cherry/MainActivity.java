package com.flamyoad.android.cherry;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity
                          implements HomeFragment.OnFragmentInteractionListener,
                                     CategoryFragment.OnFragmentInteractionListener,
                                     BookmarkFragment.OnFragmentInteractionListener,
                                     UserFragment.OnFragmentInteractionListener{

    public static final String REQUEST_SEARCH = "com.flamyoad.android.cherry.REQUEST_SEARCH";

    private Toolbar mToolbar;
    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBarLayout;

    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = mFragmentSparseArray.get(item.getItemId());
            if (fragment == null) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_category:
                        fragment = new CategoryFragment();
                        break;
                    case R.id.navigation_library:
                        fragment = new BookmarkFragment();
                        break;
                    case R.id.navigation_cherry:
                        fragment = new UserFragment();
                        break;
                }
                mFragmentSparseArray.put(item.getItemId(), fragment);
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment homeFragment = new HomeFragment();
        mFragmentSparseArray.put(R.id.navigation_home, homeFragment);
        loadFragment(homeFragment);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mSearchView = findViewById(R.id.floating_search_view);
        setupSearchView();

        mAppBarLayout = findViewById(R.id.main_appbarlayout);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView() {
        // Setting up OnFocusChangeListener
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                mSearchView.setSearchHint("Search...");
            }

            @Override
            public void onFocusCleared() {
                mSearchView.setSearchHint("Cherry Reader");
            }
        });

        // Setting up OnSearchListener
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra(REQUEST_SEARCH, currentQuery);
                startActivity(intent);
                mSearchView.clearQuery();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
