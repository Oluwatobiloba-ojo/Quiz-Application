package org.example.exception;

public class FileExistException extends RuntimeException {
    public FileExistException(String message){
        super(message);
    }
}
