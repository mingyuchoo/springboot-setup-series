package com.mingyuchoo.web.biz.user.model;

import com.mingyuchoo.web.biz.blog.model.Blog;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {

    @Id
    @Positive
    private Integer id;
    private String name;

    @OneToMany
    private List<Blog> blogs;
}
