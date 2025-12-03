package org.example.exeptions;

public class MissingAuthorizationException extends Exception{
    public MissingAuthorizationException(String message){
        super(message);
    }
    public MissingAuthorizationException(String message, Throwable cause){
        super(message, cause);
    }
}
