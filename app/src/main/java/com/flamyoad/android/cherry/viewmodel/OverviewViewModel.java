package com.flamyoad.android.cherry.viewmodel;

import android.app.Application;

import com.flamyoad.android.cherry.db.entity.Chapter;
import com.flamyoad.android.cherry.repository.OverviewRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OverviewViewModel extends AndroidViewModel {

    private OverviewRepository mRepository;

    // First tab
    private String mTitle;
    private LiveData<String> mAuthor;
    private LiveData<String> mSummary;
    private LiveData<String> mStatus;

    // Second tab
    private LiveData<List<Chapter>> mChapters;



    // Empty string initialization to prevent NPE
    private String cachedUrl = "";

    public OverviewViewModel(@NonNull Application application) {
        super(application);
        mRepository = OverviewRepository.getInstance(application);
    }

    public void initialize(String url) {
        // Prevents re-initialization in viewmodels
        if (cachedUrl.equals(url)) {
            return;
        }
        cachedUrl = url;

        // Fetches data from network
        mRepository.retrieveInfo(url);

        // Getting LiveData references from repository
        mAuthor = mRepository.getAuthor();
        mSummary = mRepository.getSummary();
        mStatus = mRepository.getStatus();
        mChapters = mRepository.getChapters();
    }


    public LiveData<String> getAuthor() {
        return mAuthor;
    }

    public LiveData<String> getSummary() {
        return mSummary;
    }

    public LiveData<String> getStatus() {
        return mStatus;
    }

    public LiveData<List<Chapter>> getChapters() {
        return mChapters;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
