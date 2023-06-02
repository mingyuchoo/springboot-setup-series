package com.example.demo.user.service;

import com.example.demo.user.model.UserVO;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserVO selectNameById(UserVO userVO) throws Exception {
        return userRepository.selectName(userVO);
    }
}
