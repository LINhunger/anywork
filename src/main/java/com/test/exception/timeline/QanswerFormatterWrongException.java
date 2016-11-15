package com.test.exception.timeline;

/**
 * Created by hunger on 2016/11/11.
 */
public class QanswerFormatterWrongException extends RuntimeException{
    public QanswerFormatterWrongException(String message) {
        super(message);
    }

    public QanswerFormatterWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
