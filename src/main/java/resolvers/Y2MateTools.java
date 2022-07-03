/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package resolvers;

import exceptions.ServiceNotAvailableException;
import exceptions.VideoNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * This class allows you to receive a information of a Youtube video through the Y2Mate service
 */
public class Y2MateTools implements YoutubeTools {
    private static final String API_SERVER = "https://154.82.111.45.sslip.io";
    private final HLSLinkTools hlsLinkTools = HLSLinkTools.getInstance();

    public VideoInfo getVideoInfo(String videoId) throws VideoNotFoundException, ServiceNotAvailableException {
        try {
            JSONObject json = getJsonFromApi(videoId);
            String streamUrl = parseStreamUrl(json);
            String hlsStreamUrl = hlsLinkTools.buildHlsUrl(streamUrl);
            return new VideoInfo(streamUrl, hlsStreamUrl);
        } catch (JSONException | InterruptedException | IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Getting first video stream link from JSON object
     */
    private String parseStreamUrl(JSONObject videoInfoJson) throws JSONException {
        return API_SERVER + videoInfoJson.getJSONObject("data").getString("mp4");
    }

    private JSONObject getJsonFromApi(String videoId)
            throws IOException, InterruptedException, JSONException, VideoNotFoundException, ServiceNotAvailableException {
        HttpClient client = prepareClient();
        HttpRequest request = prepareConvertRequest(videoId);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf("ID=%s, status: %s%n", videoId, response.statusCode());
        switch (response.statusCode()) {
            case HTTP_OK:
                return new JSONObject(response.body());
            case HTTP_BAD_REQUEST:
                throw new VideoNotFoundException(videoId);
        }

        throw new ServiceNotAvailableException("Service returned HTTP status: " + response.statusCode());
    }

    private HttpClient prepareClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private HttpRequest prepareConvertRequest(String videoId) {
        String videoUrl = "https://youtube.com/watch?v=" + videoId;
        String API_URL = API_SERVER + "/newp";

        return HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "multipart/form-data; boundary=---------------------------")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        
                        -----------------------------
                        Content-Disposition: form-data; name="u"
                        
                        %videoUrl%
                        -----------------------------
                        Content-Disposition: form-data; name="c"
                        
                        RU
                        -------------------------------
                        """.replace("%videoUrl%", videoUrl)))
                .build();
    }
}
