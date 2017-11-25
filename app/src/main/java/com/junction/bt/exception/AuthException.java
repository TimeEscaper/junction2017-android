package com.junction.bt.exception;

/**
 * Created by sibirsky on 25.11.17.
 */

public class AuthException extends Exception {

    public AuthException(String message, Exception cause) {
        super(message, cause);
    }
}
