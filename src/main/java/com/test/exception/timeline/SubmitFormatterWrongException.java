package com.test.exception.timeline;

/**
 * Created by hunger on 2016/11/11.
 */
public class SubmitFormatterWrongException extends RuntimeException{
    public SubmitFormatterWrongException(String message) {
        super(message);
    }

    public SubmitFormatterWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
