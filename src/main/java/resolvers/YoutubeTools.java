/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package resolvers;

import exceptions.ServiceNotAvailableException;
import exceptions.VideoNotFoundException;

/**
 * This interface for receive a information of a Youtube video
 */
public interface YoutubeTools {
    VideoInfo getVideoInfo(String videoId) throws VideoNotFoundException, ServiceNotAvailableException;
}
