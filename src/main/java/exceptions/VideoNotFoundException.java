/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package exceptions;

public class VideoNotFoundException extends Exception {
    public VideoNotFoundException(String videoId) {
        super("Video not found (id=" + videoId + ")");
    }
}
