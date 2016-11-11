package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class SendFormatterException extends  UserException{
    public SendFormatterException(String message) {
        super(message);
    }

    public SendFormatterException(String message, Throwable cause) {
        super(message, cause);
    }
}
