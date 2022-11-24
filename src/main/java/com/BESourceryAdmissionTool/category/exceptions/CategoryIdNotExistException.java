package com.BESourceryAdmissionTool.category.exceptions;


public class CategoryIdNotExistException extends RuntimeException {

   public CategoryIdNotExistException(Long id){
        super("Could not find Category with id: " + id);
    }
}
