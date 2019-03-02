package com.flamyoad.android.cherry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.flamyoad.android.cherry.adapter.SearchResultAdapter;
import com.flamyoad.android.cherry.db.entity.BookSearch;
import com.flamyoad.android.cherry.viewmodel.SearchActivityViewModel;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private SearchActivityViewModel viewModel;

    private RecyclerView recyclerView;

    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel.class);
        handleIntent(getIntent());

        recyclerView = findViewById(R.id.search_recyclerview);
        setupRecyclerView();
    }

    // Triggers when physical back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.wipeData();
    }

    // Triggers from clicking the up button in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        String query = intent.getStringExtra(MainActivity.REQUEST_SEARCH);
        viewModel.submitSearch(query);
    }

    private void setupRecyclerView() {
        adapter = new SearchResultAdapter(this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getBookSearches().observe(this, new Observer<List<BookSearch>>() {
            @Override
            public void onChanged(List<BookSearch> bookSearches) {
                adapter.setList(bookSearches);
            }
        });
    }
}
