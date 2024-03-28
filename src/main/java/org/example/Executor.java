package org.example;
import java.util.HashMap;

//import org.json.*;

import de.grnx.VideoMeta;
import de.grnx.YouTubeExtractorJava;
import de.grnx.YtFile;

public class Executor {
    public static void main(String[] args) {

        String youtubeLink = "https://www.youtube.com/watch?v=PopA7lronbE";

        var yt = new YouTubeExtractorJava() {
            @Override
            protected void onExtractionComplete(HashMap<Integer, YtFile> ytFiles, VideoMeta videoMeta) {

            }
        };

        var returns = yt.doInBackground(youtubeLink);
        //HashMap<Integer, YtFile>
        returns.forEach((key, value) -> {
            System.out.println(key + " : " + value.getUrl());
        });
        
    }
}