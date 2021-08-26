/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
import org.junit.jupiter.api.Test;
import resolvers.HLSLinkTools;
import utils.MD5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HLSLinkToolsTest {
    private final HLSLinkTools hlsLinkTools = HLSLinkTools.getInstance();

    @Test
    public void testLinksStorageLimit() {
        String link1 = "https://localhost/link1";
        String link2 = "https://localhost/link2";

        hlsLinkTools.buildHlsUrl(link1);
        hlsLinkTools.buildHlsUrl(link2);

        String hash1 = MD5.getMd5(link1);
        String hash2 = MD5.getMd5(link2);

        int linksStorageLimit = 10000;
        for (int linksCounter = 3; linksCounter <= linksStorageLimit + 1; linksCounter++) {
            String link = "https://localhost/link" + linksCounter;
            hlsLinkTools.buildHlsUrl(link);
        }

        //The first link must be removed after limit overflow
        assertNull(hlsLinkTools.getHlsLinkByHash(hash1));

        //The second link must remain after exceeding the limit by 1 element
        assertEquals(link2, hlsLinkTools.getHlsLinkByHash(hash2));

        //The second link must be removed after limit overflow by 2 element
        String lastLink = "https://localhost/link" + (linksStorageLimit + 2);
        hlsLinkTools.buildHlsUrl(lastLink);
        assertNull(hlsLinkTools.getHlsLinkByHash(hash2));
    }
}
