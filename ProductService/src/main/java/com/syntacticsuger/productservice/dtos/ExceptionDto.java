package com.syntacticsuger.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {
    private String message;

    public ExceptionDto(String m){
        this.message = m;
    }
}
