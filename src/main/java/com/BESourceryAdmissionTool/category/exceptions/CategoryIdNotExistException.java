package com.BESourceryAdmissionTool.category.exceptions;



public class CategoryIdNotExistException extends Exception {

   public CategoryIdNotExistException(Long id){
        super("Could not find Category with id: " + id);
    }
}
