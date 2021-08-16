import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errors.IdIsNotSpecifiedError;
import errors.APIError;
import exceptions.ServiceNotAvailableException;
import exceptions.VideoNotFoundException;
import resolvers.YoutubeTools;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.net.HttpURLConnection.*;

@WebServlet(name = "youtubeVideoServlet", value = "/youtube/get-video-info")
public class YoutubeVideoServlet extends HttpServlet {
    private final YoutubeTools youtubeTools = IoC.getYoutubeTools();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String videoId = request.getParameter("id");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        if (videoId == null) {
            response.setStatus(HTTP_BAD_REQUEST);
            outputJson(response, gson.toJson(new IdIsNotSpecifiedError()));
            return;
        }

        try{
            String streamVideoUrl = youtubeTools.getStreamUrl(videoId);
            outputJson(response, gson.toJson(new VideoInfo(streamVideoUrl)));
        } catch (VideoNotFoundException exception){
            response.setStatus(HTTP_NOT_FOUND);
            outputJson(response, gson.toJson(new APIError(exception.getMessage())));
        } catch (ServiceNotAvailableException exception){
            response.setStatus(HTTP_UNAVAILABLE);
            outputJson(response, gson.toJson(new APIError(exception.getMessage())));
        }
    }

    private void outputJson(HttpServletResponse response, String responseJSON) {
        response.setContentType("text/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(responseJSON);
        } catch(IOException e){
            e.printStackTrace();
            response.setStatus(HTTP_UNAVAILABLE);
        }
    }

    @Override
    public String getServletInfo() {
        return "Youtube tools servlet";
    }

}