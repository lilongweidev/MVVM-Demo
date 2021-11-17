package com.llw.mvvm.model;

import java.util.List;

/**
 * 壁纸列表返回数据
 * @author llw
 */
public class WallPaperResponse {
    private String msg;
    private ResBean res;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResBean {
        private List<VerticalBean> vertical;

        public List<VerticalBean> getVertical() {
            return vertical;
        }

        public void setVertical(List<VerticalBean> vertical) {
            this.vertical = vertical;
        }

        public static class VerticalBean {
            private String preview;
            private String thumb;
            private String img;
            private int views;
            private String rule;
            private int ncos;
            private int rank;
            private String source_type;
            private String wp;
            private boolean xr;
            private boolean cr;
            private int favs;
            private double atime;
            private String id;
            private String store;
            private String desc;
            private List<String> cid;
            private List<?> tag;
            private List<?> url;

            public String getPreview() {
                return preview;
            }

            public void setPreview(String preview) {
                this.preview = preview;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public int getNcos() {
                return ncos;
            }

            public void setNcos(int ncos) {
                this.ncos = ncos;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getSource_type() {
                return source_type;
            }

            public void setSource_type(String source_type) {
                this.source_type = source_type;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public boolean isXr() {
                return xr;
            }

            public void setXr(boolean xr) {
                this.xr = xr;
            }

            public boolean isCr() {
                return cr;
            }

            public void setCr(boolean cr) {
                this.cr = cr;
            }

            public int getFavs() {
                return favs;
            }

            public void setFavs(int favs) {
                this.favs = favs;
            }

            public double getAtime() {
                return atime;
            }

            public void setAtime(double atime) {
                this.atime = atime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public List<String> getCid() {
                return cid;
            }

            public void setCid(List<String> cid) {
                this.cid = cid;
            }

            public List<?> getTag() {
                return tag;
            }

            public void setTag(List<?> tag) {
                this.tag = tag;
            }

            public List<?> getUrl() {
                return url;
            }

            public void setUrl(List<?> url) {
                this.url = url;
            }
        }
    }
}

