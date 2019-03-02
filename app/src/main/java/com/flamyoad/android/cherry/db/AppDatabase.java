package com.flamyoad.android.cherry.db;

import android.content.Context;

import com.flamyoad.android.cherry.db.converter.DateTypeConverter;
import com.flamyoad.android.cherry.db.dao.BookDao;
import com.flamyoad.android.cherry.db.dao.BookSearchDao;
import com.flamyoad.android.cherry.db.entity.Book;
import com.flamyoad.android.cherry.db.entity.BookSearch;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Book.class, BookSearch.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com.flamyoad.android.cherry.AppDatabase";

    private static AppDatabase INSTANCE;

    public abstract BookDao bookDao();
    public abstract BookSearchDao bookSearchDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
