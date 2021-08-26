package resolvers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class VideoInfo {
    @Getter
    private final String stream_url;
    @Getter
    private final String stream_hls_url;
}
