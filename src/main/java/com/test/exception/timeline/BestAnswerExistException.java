package com.test.exception.timeline;

/**
 * Created by hunger on 2016/11/11.
 */
public class BestAnswerExistException extends RuntimeException{
    public BestAnswerExistException(String message) {
        super(message);
    }

    public BestAnswerExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
