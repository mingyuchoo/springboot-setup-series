package com.example.demo.user.model;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
public class UserVO {
    private Integer id;
    private String name;
}
