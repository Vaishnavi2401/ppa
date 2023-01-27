package com.cdac.announcementservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller<T> {
  
  public ResponseEntity<T> responseOk(T result) {
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  
  public ResponseEntity<List<T>> responseOk(List<T> result) {
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  
  public ResponseEntity<T> responseNotFound() {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
