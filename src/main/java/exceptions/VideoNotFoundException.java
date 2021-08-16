package exceptions;

public class VideoNotFoundException extends Exception{
    public VideoNotFoundException(String videoId) {
        super("Video not found (id=" + videoId + ")");
    }
}
