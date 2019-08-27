package com.sys.bill.service;

import com.sys.bill.common.exception.DataAccessException;
import com.sys.bill.domain.User;
import com.sys.bill.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Title: UserService
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:49
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param username
     * @return
     */
    public Optional<User> loadUser(String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        List<User> users;
        try {
            users = userRepository.findByAccountOrMobileOrEmail(username, username, username);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }

        if (users == null || users.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    /**
     * 判断用户是否存在
     *
     * @param account
     * @param mobile
     * @param email
     * @return
     */
    public boolean isUserExist(String account, String mobile, String email) {
        boolean result;

        try {
            result = userRepository.existByAccountOrMobileOrEmail(account, mobile, email);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
