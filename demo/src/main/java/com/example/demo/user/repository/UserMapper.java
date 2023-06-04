package com.example.demo.user.repository;

import com.example.demo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserMapper {

    @Transactional(readOnly = true)
    Integer selectCount(User user) throws Exception;

    @Transactional(readOnly = true)
    List<User> selectList(User user) throws Exception;

    @Transactional(readOnly = true)
    User selectItem(Integer id) throws Exception;

    Integer insertItem(User user) throws Exception;

    Integer updateItem(User user) throws Exception;

    Integer deleteItem(Integer id) throws Exception;

}
