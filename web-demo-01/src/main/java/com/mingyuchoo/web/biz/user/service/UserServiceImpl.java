package com.mingyuchoo.web.biz.user.service;

import com.mingyuchoo.web.biz.user.model.User;
import com.mingyuchoo.web.common.response.PageResponse;
import com.mingyuchoo.web.biz.user.repository.UserMapper;
import com.mingyuchoo.web.biz.user.repository.UserRepository;
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
    public PageResponse<User> selectUserList(final User user) throws Exception {
        return PageResponse.<User>builder()
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
