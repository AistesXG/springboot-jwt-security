package com.sys.bill.security;

import com.sys.bill.auth.UserRoleEnum;
import com.sys.bill.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Title: CurrentUser
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:33
 */
public class CurrentUser implements UserDetails {


    private final User user;

    private final Collection<? extends GrantedAuthority> authorities;


    public CurrentUser(User user) {
        this.user = user;
        this.authorities = AuthorityUtils.createAuthorityList(user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }

    public boolean isRoot() {
        try {
            return getUser().getRole().equalsIgnoreCase(UserRoleEnum.ROLE_ROOT.name());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public User getUser() {
        return user;
    }
}
