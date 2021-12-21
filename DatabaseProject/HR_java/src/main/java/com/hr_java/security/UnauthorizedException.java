package com.hr_java.security;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String msg){
        super(msg);
    }
    public UnauthorizedException(){
        super();
    }
}
