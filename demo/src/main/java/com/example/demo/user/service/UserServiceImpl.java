package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserMapper;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Iterable<User> findAll() throws Exception {
        return userRepository.findAll();
    }
    @Override
    public PaginationDTO<User> selectList(User user) throws Exception {
        return PaginationDTO.<User>builder()
                .count(userMapper.selectCount(user))
                .list(userMapper.selectList(user))
                .build();
    }

    @Override
    public Integer insertItem(User user) throws Exception {
        return userMapper.insertItem(user);
    }

    @Override
    public User selectItem(Integer id) throws Exception {
        return userMapper.selectItem(id);
    }

    public Integer updateItem(Integer id, User user) throws Exception {
        User updatedUser =
            User.builder()
                    .id(id)
                    .name(user.getName())
                    .build();
        return userMapper.updateItem(updatedUser);
    }

    public Integer deleteItem(Integer id) throws Exception {
        return userMapper.deleteItem(id);
    }
}
