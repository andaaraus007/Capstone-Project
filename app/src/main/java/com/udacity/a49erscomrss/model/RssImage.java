package com.udacity.a49erscomrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image", strict = false)
public class RssImage {

    @Element(name="title")
    private String title;

    @Element(name="url")
    private String url;

    @Element(name="link")
    private String link;

    public RssImage() {

    }

    public RssImage(String title, String url, String link) {
        this.title = title;
        this.url = url;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
