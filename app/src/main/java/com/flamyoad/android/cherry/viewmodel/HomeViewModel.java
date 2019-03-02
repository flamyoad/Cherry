package com.flamyoad.android.cherry.viewmodel;

import android.app.Application;
import android.content.Context;

import com.flamyoad.android.cherry.db.entity.Book;
import com.flamyoad.android.cherry.repository.BookRepository;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    private final Context mContext;

    private final BookRepository bookRepository;

    private final LiveData<List<Book>> bookList;

    private final LiveData<Boolean> shouldCancelRefreshAnimation;

    private int pageCount = 1;

    public int getPageCount() {return pageCount;}

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        bookRepository = BookRepository.getInstance(mContext);
        bookList = bookRepository.getBookList();

        bookRepository.getLatestReleases();
        shouldCancelRefreshAnimation = bookRepository.getShouldCancelRefreshAnimation();
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }

    public LiveData<Boolean> getShouldCancelRefreshAnimation() { return shouldCancelRefreshAnimation; }

    public void updateBookList() {
        pageCount++;
        BookRepository.getInstance(mContext)
                .getReleasesByPage(pageCount);
    }

    public void getLatestRelease() {
        bookRepository.getLatestReleases();
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

}
