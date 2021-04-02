package com.mingyuchoo.graphql02.entity;

import java.time.OffsetDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@ToString
@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @CreationTimestamp private OffsetDateTime createdAt;

    @UpdateTimestamp private OffsetDateTime updatedAt;

    public CommentEntity() {}

    public CommentEntity(String comment) {
        this.comment = comment;
    }
}
