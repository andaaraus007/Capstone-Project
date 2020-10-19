package com.udacity.a49erscomrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="channel", strict=false)
public class RssChannel {
    @Element(name = "title", required=false)
    private String title;

    @Element(name = "description", required=false)
    private String description;

    @Element(name = "link", required = false)
    private String link;

    @Element(name = "language", required=false)
    private String language;

    @Element(name="image", required=false)
    private RssImage image;

    @ElementList(name="item", inline=true, required=false)
    private List<RssItem> item;

    public RssChannel() {

    }

    public RssChannel(String title, String description, String link, String language, RssImage image, List<RssItem> item) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.language = language;
        this.image = image;
        this.item = item;
    }

    public List<RssItem> getItem() {
        return item;
    }
}
