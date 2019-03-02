package com.flamyoad.android.cherry.db.dao;

import com.flamyoad.android.cherry.db.entity.BookSearch;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookSearchDao {

    @Query("SELECT * FROM bookSearchResults")
    LiveData<List<BookSearch>> loadBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BookSearch> books);

    @Query("DELETE FROM bookSearchResults")
    void deleteAll();
}
