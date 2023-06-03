package com.example.demo.user.repository;

import com.example.demo.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserRepository {

    @Transactional(readOnly = true)
    List<UserModel> selectAll(UserModel userModel) throws Exception;

    @Transactional(readOnly = true)
    Integer count(UserModel userModel) throws Exception;

    @Transactional(readOnly = true)
    UserModel selectOne(Integer id) throws Exception;

    Integer insertOne(UserModel userModel) throws Exception;

    Integer updateOne(UserModel userModel) throws Exception;

    Integer deleteOne(Integer id) throws Exception;

}
