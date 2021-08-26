/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package utils;

import lombok.Getter;
import resolvers.Y2MateTools;
import resolvers.YoutubeTools;

public class IoC {
    @Getter
    private final static YoutubeTools youtubeTools = new Y2MateTools();
}
