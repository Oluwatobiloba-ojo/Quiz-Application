package org.example.exception;

public class UserAuthorizeException extends RuntimeException {
    public UserAuthorizeException(String message) {
        super(message);
    }
}
