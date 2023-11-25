package com.mingyuchoo.web.user.service;

import com.mingyuchoo.web.common.response.PageResponse;
import com.mingyuchoo.web.user.model.User;

public interface UserService {

    public Iterable<User> findAll() throws Exception;

    public PageResponse<User> selectUserList(final User user) throws Exception;

    public User selectUserItem(final Integer id) throws Exception;

    public User selectUserItemWithBlogList(final User user) throws  Exception;

    public Integer insertUserItem(final User user) throws Exception;

    public Integer updateUserItem(final Integer id, final User user) throws Exception;

    public Integer deleteUserItem(final Integer id) throws Exception;


}
