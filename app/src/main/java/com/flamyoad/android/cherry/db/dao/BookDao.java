package com.flamyoad.android.cherry.db.dao;

import com.flamyoad.android.cherry.db.entity.Book;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    LiveData<List<Book>> loadBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);

    @Query("DELETE FROM books")
    void deleteAll();
}
