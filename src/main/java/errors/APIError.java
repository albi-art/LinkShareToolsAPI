/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class APIError {
    @Getter
    protected final String message;
}
