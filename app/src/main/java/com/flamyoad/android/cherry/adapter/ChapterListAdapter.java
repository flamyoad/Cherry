package com.flamyoad.android.cherry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.db.entity.Chapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> {

    private List<Chapter> mChapterList;

    public ChapterListAdapter() {
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapterlist_row_item, parent, false);

        final ChapterViewHolder holder = new ChapterViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                // Go to reading screen
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = mChapterList.get(position);
        holder.chapterNumber.setText(chapter.getChapterNumber());
        holder.date.setText(chapter.getDate());

        if (mChapterList.get(position).isRead()) {
            // Change text to gray colour
        }
    }

    @Override
    public int getItemCount() {
        return mChapterList == null ? 0 : mChapterList.size();
    }

    public void setList(List<Chapter> chapterList) {
        mChapterList = chapterList;
        notifyDataSetChanged();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView chapterNumber;
        private TextView date;
        private ImageButton overflowButton;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapter_item_title);
            date = itemView.findViewById(R.id.chapter_item_date);
            overflowButton = itemView.findViewById(R.id.chapter_item_button);
            overflowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Popup menu here
                }
            });
        }
    }
}
