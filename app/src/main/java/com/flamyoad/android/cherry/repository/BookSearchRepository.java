package com.flamyoad.android.cherry.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.flamyoad.android.cherry.AppExecutors;
import com.flamyoad.android.cherry.db.AppDatabase;
import com.flamyoad.android.cherry.db.dao.BookSearchDao;
import com.flamyoad.android.cherry.db.entity.BookSearch;
import com.flamyoad.android.cherry.network.CherryVolley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class BookSearchRepository {

    public static final String searchUrl = "http://fanfox.net/search?title=";

    private static BookSearchRepository INSTANCE;

    private static AppDatabase appDatabase;

    private final Context context;

    private AppExecutors appExecutors;

    private BookSearchDao bookSearchDao;

    private LiveData<List<BookSearch>> bookSearches;

    public BookSearchRepository(Context context) {
        this.context = context;
        appExecutors = AppExecutors.getInstance();
        bookSearchDao = appDatabase.bookSearchDao();
        bookSearches = bookSearchDao.loadBooks();
    }

    public static BookSearchRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BookRepository.class) {
                if (INSTANCE == null) {
                    appDatabase = AppDatabase.getInstance(context);
                    INSTANCE = new BookSearchRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public void searchForBooks(String bookName) {
        final String url = searchUrl + bookName;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final List<BookSearch> bookSearches = retrieveBooks(response, url);
                appExecutors.diskIO()
                        .execute(new Runnable() {
                            @Override
                            public void run() {
                                bookSearchDao.insertAll(bookSearches);
                            }
                        });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException();
            }
        });
        CherryVolley.getInstance().addToRequestQueue(request);
    }

    public LiveData<List<BookSearch>> getBookSearches() {
        return bookSearches;
    }

    private List<BookSearch> retrieveBooks(String html, String searchUrl) {
        List<BookSearch> bookSearches = new ArrayList<>();

        Document document = Jsoup.parse(html, searchUrl);
        Elements items = document.select("ul.manga-list-4-list.line > li");

        for (Element item : items) {
            Element tag = item.selectFirst("a");
            String url = tag.absUrl("href");
            String title = tag.attr("title");
            String thumbnail = item.selectFirst("img.manga-list-4-cover").attr("src");

            String latestChap = item.selectFirst("p.manga-list-4-item-tip")
                    .select("p:nth-child(5)")
                    .text();


            BookSearch book = new BookSearch();
            book.setTitle(title);
            book.setUrl(url);
            book.setThumbnail(thumbnail);
            book.setLatestChap(latestChap);

            bookSearches.add(book);
        }

        return bookSearches;
    }

    public void wipeData() {
        appExecutors.diskIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        bookSearchDao.deleteAll();
                    }
                });
    }
}
