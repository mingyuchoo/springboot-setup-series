package com.example.demo.blog.model;

import com.example.demo.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @Positive
    private Integer id;
    private String title;

    @ManyToOne
    private User user;
}
