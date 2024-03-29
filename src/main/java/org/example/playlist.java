package org.example;

import de.grnx.YtFile;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class playlist {
    private static final Pattern patPlayerResponse = Pattern.compile("var ytInitialPlayerResponse\\s*=\\s*(\\{.+?\\})\\s*;");

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.98 Safari/537.36";//TODO steal pytube user agent collection

    public static void main(String[] args) throws IOException {
        getLinks("");
        // popular playlist i got when searching for "playlist" in youtube : https://www.youtube.com/watch?v=XXYlFuWEuKI&list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj
        //here it collected more than 215 videos even
        //i compared them to some of my own playlists with more than 100 entries in which case it only collected 99


        //scrap that, the earlier comments are wrong, it seems there is a difference wether you scrape a playlist directly with its link or use the combination of videoId and playlistId
        //example: https://www.youtube.com/watch?v=XXYlFuWEuKI&list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj
        //      https://www.youtube.com/playlist?list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj
        //as i have not yet tested the links collected, a possible explanation could be that the latter only returns the playlists videos, whereas the former returns the playlists videos and the videos recommendations itself
        //i will test this later
        System.out.println("This will only collect up to 100 videos unless the playlist is cached by youtube it seems, where it might collect more?");
    }

    public static String getLinks(String url) throws IOException {

        String pageHtml = getPlaylist(url);
        //System.out.println("pageHtml = " + pageHtml);
        String patternString = "\"watchEndpoint\":\\{\"videoId\":\"(.*?)\",";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(pageHtml);

        HashSet<String> videoIds = new HashSet<>();
        int counter = 0;
        while (matcher.find()) {
            String videoId = matcher.group(1); // group 1 is the part inside the parentheses (.*?)
            //System.out.println("Found "+ ++counter+ "th videoId: \t" + "https://www.youtube.com/watch?v="+ videoId);
            ++counter;
            videoIds.add("https://www.youtube.com/watch?v=" + videoId);
        }
        System.out.println("counter = " + counter);
        System.out.println("videoIds.size() = " + videoIds.size());
        //System.out.println("videoIds = " + videoIds.stream().collect(Collectors.joining("\n")));
        System.out.println("videoIds = " + String.join("\n", videoIds));

        return String.join("\n", videoIds);
    }

    private static String getPlaylist(String url) throws IOException {
        String pageHtml;

        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        //URL getUrl = new URL("https://youtube.com/watch?v=" + videoID);
        URL getUrl = new URL(url);
        try {
            urlConnection = (HttpURLConnection) getUrl.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sbPageHtml = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sbPageHtml.append(line);
            }
            pageHtml = sbPageHtml.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return pageHtml;
    }
}
