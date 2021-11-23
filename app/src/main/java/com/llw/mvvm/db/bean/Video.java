package com.llw.mvvm.db.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 视频
 * @author llw
 */
@Entity(tableName = "video")
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String title;
    private String share_url;
    private String author;
    private String item_cover;
    private String hot_words;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getItem_cover() {
        return item_cover;
    }

    public void setItem_cover(String item_cover) {
        this.item_cover = item_cover;
    }

    public String getHot_words() {
        return hot_words;
    }

    public void setHot_words(String hot_words) {
        this.hot_words = hot_words;
    }

    @Ignore
    public Video(String title, String share_url, String author, String item_cover, String hot_words) {
        this.title = title;
        this.share_url = share_url;
        this.author = author;
        this.item_cover = item_cover;
        this.hot_words = hot_words;
    }

    public Video() {}
}
