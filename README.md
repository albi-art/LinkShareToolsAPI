# LinkShareToolsAPI
API with tools for the Android app named LinkReceiver (https://github.com/albi-art/LinkReceiver).

## Requests
#### Get Youtube video info
    GET: /youtube/get-video-info?id=<youtube_video_id>
    
    Response: 
    200 OK
    {
      "stream_url": <stream_url>,
      "stream_hls_url": "/hls/get-video-stream.m3u8?hash=<stream_url_hash>"
    }
    
    Errors:
    400 Bad request
    404 Not found
    503 Service Unavailable

#### Get video stream
    GET: /hls/get-video-stream.m3u8?hash=<stream_url_hash>
    
    Response: 
    302 Found - redirect to <stream_url>
    
    Errors:
    400 Bad request
    404 Not found
