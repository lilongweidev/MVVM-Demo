package com.llw.mvvm.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.llw.mvvm.db.bean.Image;
import com.llw.mvvm.db.bean.WallPaper;
import com.llw.mvvm.db.dao.ImageDao;
import com.llw.mvvm.db.dao.WallPaperDao;

/**
 * 自定义Database
 *
 * @author llw
 * @description AppDatabase
 */
@Database(entities = {Image.class, WallPaper.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "mvvm_demo";
    private static volatile AppDatabase mInstance;

    /**
     * 单例模式
     */
    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return mInstance;
    }

    public abstract ImageDao imageDao();

    public abstract WallPaperDao wallPaperDao();

    /**
     * 版本升级迁移
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE `wallpaper` " +
                    "(uid INTEGER NOT NULL, " +
                    "img TEXT, " +
                    "PRIMARY KEY(`uid`))");
        }
    };


}
