package com.flamyoad.android.cherry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flamyoad.android.cherry.adapter.BookListAdapter;
import com.flamyoad.android.cherry.adapter.EndlessRecyclerViewScrollListener;
import com.flamyoad.android.cherry.db.entity.Book;
import com.flamyoad.android.cherry.viewmodel.HomeViewModel;

import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerview;
    private BookListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HomeViewModel homeViewModel;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BookListAdapter(getContext());
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerview = view.findViewById(R.id.home_recyclerview);
        mSwipeRefreshLayout = view.findViewById(R.id.home_swipelayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel.getBookList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> bookEntities) {
                mAdapter.setBookList(bookEntities);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                homeViewModel.updateBookList();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                homeViewModel.deleteAll();
                homeViewModel.getLatestRelease();
            }
        });

        homeViewModel.getShouldCancelRefreshAnimation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCancelRefreshAnim) {
                if (shouldCancelRefreshAnim) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
