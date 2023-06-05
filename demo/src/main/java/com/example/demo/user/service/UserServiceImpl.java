package com.example.demo.user.service;

import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserMapper;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PaginationDTO<User> selectUserList(final User user) throws Exception {
        return PaginationDTO.<User>builder()
                .count(userMapper.selectUserCount(user))
                .list(userMapper.selectUserList(user))
                .build();
    }

    @Override
    public User selectUserItemWithBlogList(final User user) throws Exception {
        return userMapper.selectUserItemWithBlogList(user);
    }

    @Override
    public User selectUserItem(final Integer id) throws Exception {
        return userMapper.selectUserItem(id);
    }

    @Override
    public Integer insertUserItem(final User user) throws Exception {
        if(userMapper.insertUserItem(user) != 0)
            return user.getId();
        else
            return 0;
    }

    public Integer updateUserItem(final Integer id, final User user) throws Exception {
        User updatedUser =
            User.builder()
                    .id(id)
                    .name(user.getName())
                    .build();
        return userMapper.updateUserItem(updatedUser);
    }

    public Integer deleteUserItem(final Integer id) throws Exception {
        return userMapper.deleteUserItem(id);
    }
}
