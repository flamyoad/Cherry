package com.flamyoad.android.cherry.repository;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.flamyoad.android.cherry.MyApplication;
import com.flamyoad.android.cherry.db.entity.Chapter;
import com.flamyoad.android.cherry.network.CherryVolley;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OverviewRepository {

    private static OverviewRepository INSTANCE;

    private Context mContext;

    private MutableLiveData<String> mTitle = new MutableLiveData<>();

    private MutableLiveData<String> mAuthor = new MutableLiveData<>();

    private MutableLiveData<String> mSummary = new MutableLiveData<>();

    private MutableLiveData<String> mStatus = new MutableLiveData<>();

    private MutableLiveData<List<String>> mGenres = new MutableLiveData<>();

    private MutableLiveData<List<Chapter>> mChapters = new MutableLiveData<>();

    private OverviewRepository(Context context) {
        mContext = context;
    }

    public static OverviewRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (OverviewRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OverviewRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<String> getAuthor() {
        return mAuthor;
    }

    public LiveData<String> getSummary() {
        return mSummary;
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }

    public LiveData<String> getStatus() {
        return mStatus;
    }

    public MutableLiveData<List<Chapter>> getChapters() {
        return mChapters;
    }

    public void retrieveInfo(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseForSummary(response);
                parseForChapterList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException();
            }
        });
        CherryVolley.getInstance().addToRequestQueue(request);
    }

    // ...More button prevents additional summary from being parsed
    private void parseForSummary(String html) {
        Document document = Jsoup.parse(html);
        Element frame = document.selectFirst("div.detail-info");

        String status = frame.selectFirst("span.detail-info-right-title-tip").text();
        String author = frame.selectFirst("p.detail-info-right-say")
                .selectFirst("a")
                .text();
        String summary = frame.selectFirst("p.detail-info-right-content").text();
        String title = frame.select("span.detail-info-right-title-font").text();

        List<String> genreList = new ArrayList<>();
        Elements genreNodes = frame.select("p.detail-info-right-tag-list")
                .select("a");
        for (Element genreNode : genreNodes) {
            String genre = genreNode.text();
            genreList.add(genre);
        }

        mStatus.postValue(status);
        mAuthor.postValue(author);
        mSummary.postValue(summary);
        mTitle.postValue(title);
        mGenres.postValue(genreList);
    }

    // Mangas with "Caution to under-aged viewers" will throw an exception
    private void parseForChapterList(String html) {
        Document document = Jsoup.parse(html);
        Elements chapNodes = document.select("ul.detail-main-list > li");

        if (chapNodes.size() == 0) {
            Toast.makeText(MyApplication.getContext(), "Caution to under-aged viewers", Toast.LENGTH_LONG)
                    .show();
        }

        List<Chapter> chapters = new ArrayList<>();

        String title = document.selectFirst("span.detail-info-right-title-font").text();
        for (Element chapNode : chapNodes) {
            String number = chapNode.select("p.title3").text();
            String date = chapNode.select("p.title2").text();

            Chapter chapter = new Chapter();
            chapter.setChapterNumber(number);
            chapter.setDate(date);
            chapter.setTitle(title);

            chapters.add(chapter);
        }
        mChapters.postValue(chapters);
    }
}
