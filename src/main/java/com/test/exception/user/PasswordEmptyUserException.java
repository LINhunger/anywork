package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class PasswordEmptyUserException extends RuntimeException {
    public PasswordEmptyUserException(String message) {
        super(message);
    }

    public PasswordEmptyUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
