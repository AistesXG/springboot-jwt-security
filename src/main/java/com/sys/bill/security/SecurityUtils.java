package com.sys.bill.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Title: SecurityUtils
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 13:57
 */
public class SecurityUtils {

    public SecurityUtils() {
    }

    public static boolean isRoot() {
        try {
            return getCurrentUser().isRoot();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static CurrentUser getCurrentUser() {
        try {
            Authentication authentication = getAuthentication();
            return (CurrentUser) authentication.getPrincipal();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Null authentication!");
        }
        return authentication;
    }
}
