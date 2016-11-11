package com.test.exception.user;

/**
 * Created by hunger on 2016/11/9.
 */
public class InformationFormatterFault extends RuntimeException{
    public InformationFormatterFault(String message) {
        super(message);
    }

    public InformationFormatterFault(String message, Throwable cause) {
        super(message, cause);
    }
}
