package de.grnx;

import java.util.HashMap;
import java.util.List;

public class YtVideo {
    private final List<YtFile> streamUrls;
    private final String title;
    private final String author;
    private final String videoId;
    private final String channelId;
    private final long videoLength;
    private final long viewCount;

    private final boolean isLiveStream;

    private final String shortDescription;

public YtVideo(List<YtFile> streamUrls, String title, String author, String videoId, String channelId, long videoLength, long viewCount, boolean isLiveStream, String shortDescription) {
        this.streamUrls = streamUrls;
        this.title = title;
        this.author = author;
        this.videoId = videoId;
        this.channelId = channelId;
        this.videoLength = videoLength;
        this.viewCount = viewCount;
        this.isLiveStream = isLiveStream;
        this.shortDescription = shortDescription;
    }

    public YtVideo(VideoMeta meta, YtFile ... URLS){
        this.streamUrls = List.of(URLS);
        this.title = meta.getTitle();
        this.author = meta.getAuthor();
        this.videoId = meta.getVideoId();
        this.channelId = meta.getChannelId();
        this.videoLength = meta.getVideoLength();
        this.viewCount = meta.getViewCount();
        this.isLiveStream = meta.isLiveStream();
        this.shortDescription = meta.getShortDescription();
    }

    public YtVideo(HashMap<Integer, YtFile> URLS, VideoMeta meta){
        //this.streamUrls = (List<YtFile>) URLS.values();
        this.streamUrls = List.copyOf(URLS.values());
        this.title = meta.getTitle();
        this.author = meta.getAuthor();
        this.videoId = meta.getVideoId();
        this.channelId = meta.getChannelId();
        this.videoLength = meta.getVideoLength();
        this.viewCount = meta.getViewCount();
        this.isLiveStream = meta.isLiveStream();
        this.shortDescription = meta.getShortDescription();
    }

    public List<YtFile> getStreamUrls() {
        return streamUrls;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getChannelId() {
        return channelId;
    }

    public long getVideoLength() {
        return videoLength;
    }

    public long getViewCount() {
        return viewCount;
    }

    public boolean isLiveStream() {
        return isLiveStream;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoMeta(){
        return "Title: " + title + "\nAuthor: " + author + "\nVideo ID: " + videoId + "\nChannel ID: " + channelId + "\nVideo Length: " + videoLength + "\nView Count: " + viewCount + "\nIs Live Stream: " + isLiveStream + "\nShort Description: " + shortDescription;
    }

}
