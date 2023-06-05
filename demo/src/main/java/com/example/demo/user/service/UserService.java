package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.User;

import java.util.List;

public interface UserService {

    public Iterable<User> findAll() throws Exception;

    public PaginationDTO<User> selectUserList(final User user) throws Exception;

    public User selectUserItem(final Integer id) throws Exception;

    public User selectUserItemWithBlogList(final User user) throws  Exception;

    public Integer insertUserItem(final User user) throws Exception;

    public Integer updateUserItem(final Integer id, final User user) throws Exception;

    public Integer deleteUserItem(final Integer id) throws Exception;


}
