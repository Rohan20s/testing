package com.example.jsonextract;

public class Img {
    String pageId, title, timeStamp,url,descriptionUrl;

    public Img(String pageId, String title, String timeStamp, String url, String descriptionUrl) {
        this.pageId = pageId;
        this.title = title;
        this.timeStamp = timeStamp;
        this.url = url;
        this.descriptionUrl = descriptionUrl;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }
}
