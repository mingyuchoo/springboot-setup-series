package com.mingyuchoo.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String passport;
}
