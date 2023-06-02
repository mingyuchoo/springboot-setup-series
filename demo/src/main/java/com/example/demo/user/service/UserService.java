package com.example.demo.user.service;

import com.example.demo.user.model.UserVO;

public interface UserService {
    public UserVO selectNameById(UserVO userVO) throws Exception;
}
