package com.flamyoad.android.cherry.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flamyoad.android.cherry.R;
import com.flamyoad.android.cherry.db.entity.Book;
import com.flamyoad.android.cherry.ui.MangaOverviewActivity;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    public static final String BOOK_NAME = "com.flamyoad.android.cherry.adapter.BOOK_NAME";
    public static final String BOOK_URL = "com.flamyoad.android.cherry.adapter.BOOK_URL";
    public static final String BOOK_IMG = "com.flamyoad.android.cherry.adapter.BOOK_IMG";


    private final Context mContext;
    private List<Book> bookList = Collections.emptyList();

    public BookListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        final BookViewHolder holder = new BookViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = bookList.get(holder.getAdapterPosition());
                Context viewContext = view.getContext();
                Intent intent = new Intent(viewContext, MangaOverviewActivity.class);
                intent.putExtra(BOOK_NAME, book.getTitle());
                intent.putExtra(BOOK_URL, book.getUrl());
                intent.putExtra(BOOK_IMG, book.getThumbnail());
                viewContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleText.setText(book.getTitle());
        holder.chapText.setText(book.getLatestChap());
        Glide.with(mContext)
                .load(book.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

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
