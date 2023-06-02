package com.example.demo.common.model;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
public class CommonReturnDTO<T> {
    private String successOrNot;
    private String statusCode;
    private T data;
}
