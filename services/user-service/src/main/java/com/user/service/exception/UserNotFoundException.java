package com.user.service.exception;

import org.aspectj.bridge.IMessage;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }
}
