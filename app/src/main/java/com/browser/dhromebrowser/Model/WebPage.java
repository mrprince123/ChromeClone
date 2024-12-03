package com.browser.dhromebrowser.Model;

public class WebPage {


    private String webpage_image;
    private String name;
    private String webpage_url;

    public WebPage(String webpage_image, String name, String webpage_url) {
        this.webpage_image = webpage_image;
        this.name = name;
        this.webpage_url = webpage_url;
    }

    public String getWebpage_image() {
        return webpage_image;
    }

    public void setWebpage_image(String webpage_image) {
        this.webpage_image = webpage_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebpage_url() {
        return webpage_url;
    }

    public void setWebpage_url(String webpage_url) {
        this.webpage_url = webpage_url;
    }
}
