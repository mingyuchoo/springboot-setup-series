package com.mingyuchoo.graphql.repository;

import com.mingyuchoo.graphql.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
