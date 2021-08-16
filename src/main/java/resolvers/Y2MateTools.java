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

    @Override
    public String getStreamUrl(String videoId) throws VideoNotFoundException, ServiceNotAvailableException {
        try {
            JSONObject json = getJsonFromApi(videoId);
            return parseStreamUrl(json);
        } catch (JSONException | InterruptedException | IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private String parseStreamUrl(JSONObject videoInfoJson) throws JSONException {
        return videoInfoJson.getJSONArray("url").getJSONObject(0).getString("url");
    }

    private JSONObject getJsonFromApi(String videoId)
            throws IOException, InterruptedException, JSONException, VideoNotFoundException, ServiceNotAvailableException {
        HttpClient client = prepareClient();
        HttpRequest request = prepareConvertRequest(videoId);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf("ID=%s, status: %s%n", videoId, response.statusCode());
        switch (response.statusCode()){
            case HTTP_OK:
                return new JSONObject(response.body());
            case HTTP_BAD_REQUEST:
                throw new VideoNotFoundException(videoId);
        }

        throw new ServiceNotAvailableException("Service returned HTTP status: " + response.statusCode());
    }

    private HttpClient prepareClient(){
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private HttpRequest prepareConvertRequest(String videoId){
        String videoUrl = "https://youtube.com/watch?v=" + videoId;
        String API_URL = "https://api.y2mate.guru/api/convert";

        return HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"url\":\"" + videoUrl + "\"}"))
                .build();
    }
}