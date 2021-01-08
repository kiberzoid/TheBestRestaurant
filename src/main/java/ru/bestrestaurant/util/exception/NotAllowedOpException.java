package ru.bestrestaurant.util.exception;

public class NotAllowedOpException extends RuntimeException{
    public NotAllowedOpException(String message){
        super(message);
    }
}
