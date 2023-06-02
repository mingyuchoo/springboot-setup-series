package com.example.demo.user.repository;

import com.example.demo.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface UserRepository {

    @Transactional(readOnly = true)
    UserVO selectName(UserVO userVO) throws Exception;

}
