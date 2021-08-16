package errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class APIError {
    @Getter
    protected final String message;
}
