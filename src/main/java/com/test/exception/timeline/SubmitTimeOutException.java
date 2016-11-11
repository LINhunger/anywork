package com.test.exception.timeline;

/**
 * Created by hunger on 2016/11/11.
 */
public class SubmitTimeOutException extends RuntimeException{
    public SubmitTimeOutException(String message) {
        super(message);
    }

    public SubmitTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
