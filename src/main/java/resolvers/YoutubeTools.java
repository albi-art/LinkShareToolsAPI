package resolvers;

import exceptions.ServiceNotAvailableException;
import exceptions.VideoNotFoundException;

/**
 * This interface for receive a information of a Youtube video
 */
public interface YoutubeTools {
    String getStreamUrl(String videoId) throws VideoNotFoundException, ServiceNotAvailableException;
}
