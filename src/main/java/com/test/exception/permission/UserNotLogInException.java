package com.test.exception.permission;

/**
 * Created by hunger on 2016/11/14.
 */
public class UserNotLogInException extends RuntimeException {
    public UserNotLogInException(String message) {
        super(message);
    }

    public UserNotLogInException(String message, Throwable cause) {
        super(message, cause);
    }
}
