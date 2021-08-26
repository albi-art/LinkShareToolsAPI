/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package errors;

public class IdIsNotSpecifiedError extends APIError {
    public IdIsNotSpecifiedError() {
        super("Url parameter ID is not specified (add to url end ?id=<video_id>)");
    }
}
