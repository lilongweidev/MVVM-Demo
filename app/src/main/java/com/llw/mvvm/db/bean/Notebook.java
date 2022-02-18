package com.llw.mvvm.db.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 笔记
 * @author llw
 */
@Entity(tableName = "notebook")
public class Notebook {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String title;
    private String content;
    @Ignore
    private boolean isSelect;//是否选中

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Ignore
    public Notebook(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Notebook() {}
}
