package com.flamyoad.android.cherry.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.flamyoad.android.cherry.AppExecutors;
import com.flamyoad.android.cherry.db.AppDatabase;
import com.flamyoad.android.cherry.db.dao.BookDao;
import com.flamyoad.android.cherry.db.entity.Book;
import com.flamyoad.android.cherry.network.CherryVolley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BookRepository {
    public static final String MANGAFOX = "MANGAFOX-";

    public static final String latestUpdatesUrl = "http://fanfox.net/releases/";

    private Context context;

    private AppExecutors appExecutors;

    private static BookRepository INSTANCE;

    private static AppDatabase appDatabase;

    private BookDao bookDao;

    private LiveData<List<Book>> bookList;

    private MutableLiveData<Boolean> shouldCancelRefreshAnimation = new MutableLiveData<>();

    private BookRepository(Context context) {
        this.context = context;
        bookDao = appDatabase.bookDao();
        bookList = bookDao.loadBooks();

        appExecutors = AppExecutors.getInstance();
        shouldCancelRefreshAnimation.setValue(false);
    }

    public static BookRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BookRepository.class) {
                if (INSTANCE == null) {
                    appDatabase = AppDatabase.getInstance(context);
                    INSTANCE = new BookRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }

    public LiveData<Boolean> getShouldCancelRefreshAnimation() {return shouldCancelRefreshAnimation;}

    public void deleteAll() {
        appExecutors.diskIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        bookDao.deleteAll();
                    }
                });
    }

    public void getLatestReleases() {
        shouldCancelRefreshAnimation.setValue(false);
        StringRequest request = new StringRequest(Request.Method.GET, latestUpdatesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final List<Book> books = retrieveBooks(response);
                shouldCancelRefreshAnimation.setValue(true);
                appExecutors
                        .diskIO()
                        .execute(new Runnable() {
                            @Override
                            public void run() {
                                bookDao.deleteAll();
                                bookDao.insertAll(books);
                            }
                        });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException("Error occured at getLatestReleases() in BookRepository \n" + error.getMessage());
            }
        });

        CherryVolley.getInstance().addToRequestQueue(request);
    }

    public void getReleasesByPage(final int pageNumber) {
        StringRequest request = new StringRequest(Request.Method.GET, getDirectoryUrlByPage(pageNumber), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                bookList.getValue().addAll(retrieveBooks(response));
                final List<Book> books = retrieveBooks(response);
                appExecutors
                        .diskIO()
                        .execute(new Runnable() {
                            @Override
                            public void run() {
                                bookDao.insertAll(books);
                            }
                        });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException("Error occured at getLatestReleases() in BookRepository +" +
                        "page number = " + pageNumber);
            }
        });
        CherryVolley.getInstance().addToRequestQueue(request);
    }

    public List<Book> retrieveBooks(String html) {
        List<Book> bookList = new ArrayList<>();

        Document document = Jsoup.parse(html, latestUpdatesUrl);
        Elements items = document.select("ul.manga-list-4-list.line > li");
        for (Element item : items) {
            Element tag = item.selectFirst("a");
//            String url = tag.attr("href");
            String url = tag.absUrl("href");
            String title = tag.attr("title");
            String thumbnail = item.selectFirst("img.manga-list-4-cover").attr("src");

            String latestChap = item.selectFirst("ul.manga-list-4-item-part")
                    .selectFirst("a")
                    .text();

            Book book = new Book();
            book.setTitle(title);
            book.setUrl(url);
            book.setThumbnail(thumbnail);
            book.setLatestChap(latestChap);
            book.setUniqueName(MANGAFOX + title);

            bookList.add(book);
        }
        return bookList;
    }

    private String getDirectoryUrlByPage(int pageNumber) {
        return latestUpdatesUrl + Integer.toString(pageNumber) + ".html";
    }

}
