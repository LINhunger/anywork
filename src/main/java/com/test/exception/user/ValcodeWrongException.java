package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class ValcodeWrongException extends RuntimeException{

    public ValcodeWrongException(String message) {
        super(message);
    }

    public ValcodeWrongException(String message, Throwable cause) {
        super(message, cause);
    }

}
