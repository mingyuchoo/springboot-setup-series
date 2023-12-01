package com.mingyuchoo.web.user.service;

import com.mingyuchoo.web.common.response.PageResponse;
import com.mingyuchoo.web.user.model.User;

public interface UserService {

    Iterable<User> findAll() throws Exception;

    PageResponse<User> selectUserList(final User user) throws Exception;

    User selectUserItem(final Integer id) throws Exception;

    User selectUserItemWithBlogList(final User user) throws  Exception;

    Integer insertUserItem(final User user) throws Exception;

    Integer updateUserItem(final Integer id, final User user) throws Exception;

    Integer deleteUserItem(final Integer id) throws Exception;


}
