package com.wuyz.demos;

/**
 * Created by wuyz on 2017/1/12.
 * MovieData
 */

public class MovieData {

    private String nm;
    private String desc;
    private String img;
    private String pubDesc;
    private String showInfo;
    private String comingTitle;

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPubDesc() {
        return pubDesc;
    }

    public void setPubDesc(String pubDesc) {
        this.pubDesc = pubDesc;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getComingTitle() {
        return comingTitle;
    }

    public void setComingTitle(String comingTitle) {
        this.comingTitle = comingTitle;
    }

    @Override
    public String toString() {
        return String.format("%s %s", nm, img);
    }
}
