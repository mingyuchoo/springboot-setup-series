package com.mingyuchoo.web.biz.user.repository;

import com.mingyuchoo.web.biz.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserMapper {

    @Transactional(readOnly = true)
    Integer selectUserCount(final User user) throws Exception;

    @Transactional(readOnly = true)
    List<User> selectUserList(final User user) throws Exception;

    @Transactional(readOnly = true)
    User selectUserItemWithBlogList(final User user) throws Exception;

    @Transactional(readOnly = true)
    User selectUserItem(final Integer id) throws Exception;

    Integer insertUserItem(final User user) throws Exception;

    Integer updateUserItem(final User user) throws Exception;

    Integer deleteUserItem(final Integer id) throws Exception;

}
