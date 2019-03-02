package com.flamyoad.android.cherry.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.viewmodel.OverviewViewModel;


public class SummaryFragment extends Fragment {

    private OverviewViewModel mViewModel;

    private TextView authorTextview;
    private TextView summaryTextview;

    public SummaryFragment() {

    }

    public static SummaryFragment newInstance() {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OverviewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_summary, container, false);
        authorTextview = view.findViewById(R.id.author_textview);
        summaryTextview = view.findViewById(R.id.summary_textview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getAuthor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.trim().equals("")) {
                    authorTextview.setText("N/A");
                } else {
                    authorTextview.setText(s);
                }
            }
        });
        mViewModel.getSummary().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.trim().equals("")) {
                    summaryTextview.setText("N/A");
                } else {
                    summaryTextview.setText(s);
                }
            }
        });
    }
}
