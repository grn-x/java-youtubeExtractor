package org.example;

import de.grnx.VideoMeta;
import de.grnx.YtVideoFactory;

public class Executor {
    public static void main(String[] args) {

        String youtubeLink = "https://www.youtube.com/watch?v=y65U8omaAq4";

        final VideoMeta[] meta = new VideoMeta[1];
        var yt = new YtVideoFactory();

        var returns = yt.executes(youtubeLink);


        returns.getStreamUrls().forEach(System.out::println);

        System.out.println(returns.getVideoMeta());
        //System.out.println("meta[0] = " + meta[0]);

    }
}