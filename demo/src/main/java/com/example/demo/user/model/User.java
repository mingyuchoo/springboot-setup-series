package com.example.demo.user.model;

import com.example.demo.blog.model.Blog;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Positive
    private Integer id;
    private String name;

    @OneToMany
    private List<Blog> blogs;
}
