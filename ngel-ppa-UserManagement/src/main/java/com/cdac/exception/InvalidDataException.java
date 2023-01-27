package com.cdac.exception;

import java.io.IOException; 

/**
 * This exception is used when a file contains illegal or unexpected data. 
 * 
 * @author Ramu Parupalli
 */ 
public class InvalidDataException extends IOException { 
 
    private static final long serialVersionUID = 1L; 
 
    public InvalidDataException() { 
        super("Invalid Data!"); 
    } 
 
    public InvalidDataException(String s) { 
        super(s); 
    } 
}
