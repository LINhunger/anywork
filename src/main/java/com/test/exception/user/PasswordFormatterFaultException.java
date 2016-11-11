package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class PasswordFormatterFaultException extends  RuntimeException{
    public PasswordFormatterFaultException(String message) {
        super(message);
    }

    public PasswordFormatterFaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
