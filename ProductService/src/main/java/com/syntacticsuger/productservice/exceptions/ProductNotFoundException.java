package com.syntacticsuger.productservice.exceptions;


public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message){
        super(message);
    }
}
