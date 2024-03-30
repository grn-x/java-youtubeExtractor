package org.example;

import de.grnx.YtFile;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class playlist {
    private static final Pattern patPlayerResponse = Pattern.compile("var ytInitialPlayerResponse\\s*=\\s*(\\{.+?\\})\\s*;");

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.98 Safari/537.36";//TODO steal pytube user agent collection

    public static void main(String[] args) throws IOException {
        //getLinks("https://www.youtube.com/playlist?list=xxx");
        getLinks("https://www.youtube.com/playlist?list=xxx");
        //html scraping like this is limited to 100 videos
        System.out.println("This will only collect up to 100 videos");
    }

    public static String getLinks(String url) throws IOException {

        String pageHtml = getPlaylist(url);
        //System.out.println("pageHtml = " + pageHtml);
        File workingDirectory =new File("./").getAbsoluteFile().getParentFile(); //this clusterfuck is necessary for creating a folder in the working directory without having to make love to java awt just to maybe get the desktop path in win only
        File directory = new File(workingDirectory.getAbsolutePath()+"\\FakeCache");
        Files.write(Paths.get(directory.getAbsolutePath() + "\\playlist.html"), pageHtml.getBytes());

        //TODO: properly escape characters such as new line and tab codepoints
        String numVideosTextString = "\"numVideosText\":\\{\"runs\":\\[\\{\"text\":\"(.*?)\"\\},";
        Pattern patternNumVideos = Pattern.compile(numVideosTextString);
        Matcher matcherNumVideos = patternNumVideos.matcher(pageHtml);
        if(matcherNumVideos.find()){
            String numVideos = matcherNumVideos.group(1);
            System.out.println("Number of Videos inside the Playlist: " + numVideos);
        }

        String titleString = "\"title\":\\{\"simpleText\":\"(.*?)\"\\},";
        Pattern patternTitle = Pattern.compile(titleString);
        Matcher matcherTitle = patternTitle.matcher(pageHtml);
        if(matcherTitle.find()){
            String title = matcherTitle.group(1);
            System.out.println("Title = " + title);
        }

        String ownerString = "\"ownerText\":\\{\"runs\":\\[\\{\"text\":\"(.*?)\",";
        Pattern patternOwner = Pattern.compile(ownerString);
        Matcher matcherOwner = patternOwner.matcher(pageHtml);
        if(matcherOwner.find()){
            String owner = matcherOwner.group(1);
            System.out.println("Owner = " + owner);
        }

        String descriptionString = "\"descriptionText\":\\{\"simpleText\":\"(.*?)\"\\},";
        Pattern patternDescription = Pattern.compile(descriptionString);
        Matcher matcherDescription = patternDescription.matcher(pageHtml);
        if(matcherDescription.find()){
            String description = matcherDescription.group(1);
            System.out.println("Description = " + description);
        }

        String patternVideosString = "\"watchEndpoint\":\\{\"videoId\":\"(.*?)\",";
        Pattern patternVideos = Pattern.compile(patternVideosString);
        Matcher matcherVideos = patternVideos.matcher(pageHtml);

        HashSet<String> videoIds = new HashSet<>();
        int counter = 0;

        while (matcherVideos.find()) {
            String videoId = matcherVideos.group(1);
            ++counter;
            videoIds.add("https://www.youtube.com/watch?v=" + videoId);
        }
        System.out.println("Total amount of Video URLs contained in HTML: " + counter);
        System.out.println("Unique collected Video URLs = " + videoIds.size());
        //System.out.println("videoIds = " + videoIds.stream().collect(Collectors.joining("\n")));
        System.out.println("\nThe Playlist contains following Videos:\n" + String.join("\n", videoIds));

        return String.join(",\n", videoIds);
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
