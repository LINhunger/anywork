package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class UserException extends  RuntimeException{

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
