/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package resolvers;

import lombok.Getter;
import utils.MD5;

import java.util.LinkedHashMap;
import java.util.Map;

public class HLSLinkTools {
    @Getter
    private final String hlsUrlPrefix = "/hls/get-video-stream.m3u8?hash=";

    private final int maxLinksMapSize = 10000;
    private final Map<String, String> hlsLinksMap = new LinkedHashMap<>(maxLinksMapSize) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > maxLinksMapSize;
        }
    };

    private static HLSLinkTools instance;

    private HLSLinkTools() {
    }

    public static HLSLinkTools getInstance() {
        return instance == null
                ? instance = new HLSLinkTools()
                : instance;
    }

    public String buildHlsUrl(String url) {
        String urlMd5Hash = MD5.getMd5(url);
        hlsLinksMap.put(urlMd5Hash, url);
        return hlsUrlPrefix + urlMd5Hash;
    }

    public String getHlsLinkByHash(String hash) {
        return hlsLinksMap.get(hash);
    }
}
