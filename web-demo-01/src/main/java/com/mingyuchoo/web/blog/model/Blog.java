package com.mingyuchoo.web.blog.model;

import com.mingyuchoo.web.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
@Entity
public class Blog {

    @Id
    @Positive
    private Integer id;
    private String title;

    @ManyToOne
    private User user;
}
