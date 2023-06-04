package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.entity.User;

public interface UserService {

    public Iterable<User> findAll() throws Exception;

    public PaginationDTO<User> selectList(User user) throws Exception;

    public Integer insertItem(User user) throws Exception;

    public User selectItem(Integer id) throws Exception;

    public Integer updateItem(Integer id, User user) throws Exception;

    public Integer deleteItem(Integer id) throws Exception;


}
