package com.sys.bill.security;

import com.sys.bill.domain.User;
import com.sys.bill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Title: UserDetailServiceImpl
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:32
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user =  userService.loadUser(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", s)));
        return new CurrentUser(user);
    }
}
