package com.flamyoad.android.cherry.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flamyoad.android.cherry.MyApplication;

// Never subclass Application
// Context instance is only created in onCreate() method
public class CherryVolley {

    private static CherryVolley INSTANCE;

    private final RequestQueue requestQueue;

    private final Context CONTEXT;

    private CherryVolley() {
        CONTEXT = MyApplication.getContext();
        requestQueue = Volley.newRequestQueue(CONTEXT);
    }

    public static synchronized CherryVolley getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CherryVolley();
        }
        return INSTANCE;
    }

    public void addToRequestQueue(StringRequest request) {
        if (!hasInternetConnection()) {
            Toast.makeText(CONTEXT, "No internet connection", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        requestQueue.add(request);
    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
