package com.test.exception;

/**
 * Created by hunger on 2016/11/11.
 */
public class CauseTroubleException  extends RuntimeException{
    public CauseTroubleException(String message) {
        super(message);
    }

    public CauseTroubleException(String message, Throwable cause) {
        super(message, cause);
    }
}
