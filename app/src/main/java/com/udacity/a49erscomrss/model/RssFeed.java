package com.udacity.a49erscomrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="rss", strict=false)
public class RssFeed {
    @Element(name="channel")
    private RssChannel channel;

    public RssFeed() {

    }
    public RssFeed(RssChannel channel) {
        this.channel = channel;
    }

    public RssChannel getChannel() {
        return channel;
    }
}
