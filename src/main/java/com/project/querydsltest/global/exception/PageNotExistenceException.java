package com.project.querydsltest.global.exception;

public class PageNotExistenceException extends
    NullPointerException {

    public PageNotExistenceException(String message){
        super(message);
    }
}
