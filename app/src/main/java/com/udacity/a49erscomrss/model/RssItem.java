package com.udacity.a49erscomrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name="item", strict=false)
public class RssItem implements Serializable {

    @Element(name="title", required=false)
    private String title;

    @Element(name="description", required=false)
    private String description;

    @Element(name="link", required=false)
    private String link;

    @Element(name="guid", required=false)
    private String guid;

    private boolean isSaved = false;

    public RssItem() {

    }

    public RssItem(String guid, String title, String description, String link, boolean isSaved) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.link = link;
        this.isSaved = isSaved;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
}
