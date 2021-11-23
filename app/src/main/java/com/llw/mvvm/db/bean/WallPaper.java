package com.llw.mvvm.db.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 壁纸
 * @author llw
 */
@Entity(tableName = "wallpaper")
public class WallPaper {

    @PrimaryKey(autoGenerate = true)
    private int uid = 0;

    private String img;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public WallPaper() {}

    @Ignore
    public WallPaper(String img) {
        this.img = img;
    }
}
