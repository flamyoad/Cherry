package com.flamyoad.android.cherry.viewmodel;

import android.app.Application;
import android.content.Context;

import com.flamyoad.android.cherry.db.entity.BookSearch;
import com.flamyoad.android.cherry.repository.BookSearchRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SearchActivityViewModel extends AndroidViewModel {

    private final Context context;

    private final BookSearchRepository repository;

    private LiveData<List<BookSearch>> bookSearches;

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);
        context = application;
        repository = BookSearchRepository.getInstance(context);
        bookSearches = repository.getBookSearches();
    }

    public LiveData<List<BookSearch>> getBookSearches() {
        return bookSearches;
    }

    public void submitSearch(String query) {
        repository.searchForBooks(query);
    }

    public void wipeData() {
        repository.wipeData();
    }
}
