package com.flamyoad.android.cherry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.db.entity.BookSearch;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultBookHolder> {

    private final Context context;
    private List<BookSearch> bookSearches;

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);
        return new ResultBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultBookHolder holder, int position) {
        BookSearch book = bookSearches.get(position);
        holder.titleText.setText(book.getTitle());
        holder.chapText.setText(book.getLatestChap());
        Glide.with(context)
                .load(book.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookSearches == null ? 0 : bookSearches.size();
    }

    public void setList(List<BookSearch> searches) {
        bookSearches = searches;
        notifyDataSetChanged();
    }

    class ResultBookHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleText;
        private TextView chapText;

        public ResultBookHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_list_item_img);
            titleText = itemView.findViewById(R.id.home_list_item_title);
            chapText = itemView.findViewById(R.id.home_list_item_chap);
        }
    }
}
