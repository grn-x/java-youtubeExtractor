package org.example;

import de.grnx.VideoMeta;
import de.grnx.YtVideoFactory;

public class Executor {
    public static void main(String[] args) {

        String youtubeLink = "https://www.youtube.com/watch?v=lf2WpjGdEME";

        final VideoMeta[] meta = new VideoMeta[1];
        var yt = new YtVideoFactory();

        var result = yt.executes(youtubeLink);


        result.getStreamUrls().forEach(System.out::println);

        System.out.println(result.getVideoMeta());
        //System.out.println("meta[0] = " + meta[0]);

    }
}