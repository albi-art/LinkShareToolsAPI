package errors;

public class IdIsNotSpecifiedError extends APIError {
    public IdIsNotSpecifiedError() {
        super("Url parameter ID is not specified (add to url end ?id=<video_id>)");
    }
}
