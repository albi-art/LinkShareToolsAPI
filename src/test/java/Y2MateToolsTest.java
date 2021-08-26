/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
import exceptions.ServiceNotAvailableException;
import exceptions.VideoNotFoundException;
import org.junit.jupiter.api.Test;
import resolvers.VideoInfo;
import resolvers.Y2MateTools;
import resolvers.YoutubeTools;
import utils.MD5;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Y2MateToolsTest {
    YoutubeTools youtubeTools = new Y2MateTools();

    @Test
    void test(){
        try {
            VideoInfo videoInfo = youtubeTools.getVideoInfo("5Y62GFwVszI");
            String expectedHlsLink = "/hls/get-video-stream.m3u8?hash=" + MD5.getMd5(videoInfo.getStream_url());
            assertEquals(expectedHlsLink, videoInfo.getStream_hls_url());
        } catch (VideoNotFoundException | ServiceNotAvailableException exception) {
            exception.printStackTrace();
        }
    }
}
