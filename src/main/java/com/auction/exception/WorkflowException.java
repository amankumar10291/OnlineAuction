package com.auction.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowException extends RuntimeException{
    private int status;
    private String message;
}
