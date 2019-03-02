package com.flamyoad.android.cherry.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.adapter.ChapterListAdapter;
import com.flamyoad.android.cherry.db.entity.Chapter;
import com.flamyoad.android.cherry.viewmodel.OverviewViewModel;

import java.util.List;

public class ChapterListFragment extends Fragment {

    private OverviewViewModel mViewModel;

    private RecyclerView mRecyclerView;

    private ChapterListAdapter mChapterListAdapter;

    public ChapterListFragment() {
    }

    public static ChapterListFragment newInstance() {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OverviewViewModel.class);
        mChapterListAdapter = new ChapterListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapterlist, container, false);
        mRecyclerView = view.findViewById(R.id.chapterlist_recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getChapters().observe(this, new Observer<List<Chapter>>() {
            @Override
            public void onChanged(List<Chapter> chapters) {
                mChapterListAdapter.setList(chapters);
            }
        });
        mRecyclerView.setAdapter(mChapterListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}
