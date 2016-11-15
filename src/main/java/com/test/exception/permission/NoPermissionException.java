package com.test.exception.permission;

/**
 * Created by hunger on 2016/11/14.
 */
public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
