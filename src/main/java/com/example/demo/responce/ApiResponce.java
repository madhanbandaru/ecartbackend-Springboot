package com.example.demo.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponce<T> {
    private boolean success;
    private String message;
    private T data;
}
