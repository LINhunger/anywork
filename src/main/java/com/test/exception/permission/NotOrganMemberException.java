package com.test.exception.permission;

/**
 * Created by hunger on 2016/11/14.
 */
public class NotOrganMemberException extends RuntimeException{
    public NotOrganMemberException(String message) {
        super(message);
    }

    public NotOrganMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
