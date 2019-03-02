package com.flamyoad.android.cherry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.db.entity.Book;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

// TODO: Replace with ListAdapter
public class PagedBookListAdapter extends PagedListAdapter<Book, PagedBookListAdapter.BookViewHolder> {

    public PagedBookListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        holder.titleText.setText(book.getTitle());
        holder.chapText.setText(book.getLatestChap());
        Glide.with(holder.itemView.getContext())
                .load(book.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    private static DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                    return oldItem.getUniqueName().equals(newItem.getUniqueName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };



    class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleText;
        private TextView chapText;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_list_item_img);
            titleText = itemView.findViewById(R.id.home_list_item_title);
            chapText = itemView.findViewById(R.id.home_list_item_chap);
        }
    }
}
