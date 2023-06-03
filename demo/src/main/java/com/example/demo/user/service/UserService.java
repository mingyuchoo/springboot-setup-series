package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.UserModel;

public interface UserService {

    public PaginationDTO<UserModel> selectAll(UserModel userModel) throws Exception;

    public Integer insertOne(UserModel userModel) throws Exception;

    public UserModel selectOne(Integer id) throws Exception;

    public Integer updateOne(Integer id, UserModel userModel) throws Exception;

    public Integer deleteOne(Integer id) throws Exception;

}
