package com.joosangah.ordersystem.common.exception;

public class DuplicateException extends RuntimeException{

    public DuplicateException() {
        super("Element with the specified ID already exists");
    }
}
