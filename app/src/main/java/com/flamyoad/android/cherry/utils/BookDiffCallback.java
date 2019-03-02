package com.flamyoad.android.cherry.utils;

import com.flamyoad.android.cherry.db.entity.Book;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class BookDiffCallback extends DiffUtil.Callback {

    private final List<Book> oldList;
    private final List<Book> newList;

    public BookDiffCallback(List<Book> oldList, List<Book> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final Book oldBook = oldList.get(oldItemPosition);
        final Book newBook = newList.get(newItemPosition);
        return newBook.getTitle().equals(oldBook.getTitle());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
