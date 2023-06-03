package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.UserModel;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public PaginationDTO<UserModel> selectAll(UserModel userModel) throws Exception {
        return PaginationDTO.<UserModel>builder()
                .count(userRepository.count(userModel))
                .list(userRepository.selectAll(userModel))
                .build();
    }

    @Override
    public Integer insertOne(UserModel userModel) throws Exception {
        return userRepository.insertOne(userModel);
    }

    @Override
    public UserModel selectOne(Integer id) throws Exception {
        return userRepository.selectOne(id);
    }

    public Integer updateOne(Integer id, UserModel userModel) throws Exception {
        UserModel updatedUserModel =
            UserModel.builder()
                    .id(id)
                    .name(userModel.getName())
                    .build();
        return userRepository.updateOne(updatedUserModel);
    }

    public Integer deleteOne(Integer id) throws Exception {
        return userRepository.deleteOne(id);
    }
}
