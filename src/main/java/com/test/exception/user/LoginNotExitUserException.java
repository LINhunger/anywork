package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class LoginNotExitUserException extends RuntimeException {

    public LoginNotExitUserException(String message) {
        super(message);
    }

    public LoginNotExitUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
