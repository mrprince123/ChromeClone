package com.browser.dhromebrowser.Model;

public class News {

    private String newsImage;
    private String newsTitle;
    private String newsProviderLogo;
    private String newsProviderName;
    private String newsTime;
    private String newsLink;

    public News(String newsImage, String newsTitle, String newsProviderLogo, String newsProviderName, String newsTime, String newsLink) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsProviderLogo = newsProviderLogo;
        this.newsProviderName = newsProviderName;
        this.newsTime = newsTime;
        this.newsLink = newsLink;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsProviderLogo() {
        return newsProviderLogo;
    }

    public void setNewsProviderLogo(String newsProviderLogo) {
        this.newsProviderLogo = newsProviderLogo;
    }

    public String getNewsProviderName() {
        return newsProviderName;
    }

    public void setNewsProviderName(String newsProviderName) {
        this.newsProviderName = newsProviderName;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }


}
