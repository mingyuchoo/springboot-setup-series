package com.mingyuchoo.graphql02.repository;

import com.mingyuchoo.graphql02.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<CommentEntity, Long> {}