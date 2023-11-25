package com.mingyuchoo.web.common.response;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
public class JsonResponse<T> {
    private String successOrNot;
    private String statusCode;
    private T data;
}
