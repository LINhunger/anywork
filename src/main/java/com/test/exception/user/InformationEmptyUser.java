package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class InformationEmptyUser extends RuntimeException{
    public InformationEmptyUser(String message) {
        super(message);
    }

    public InformationEmptyUser(String message, Throwable cause) {
        super(message, cause);
    }
}
