package com.sys.bill.auth;

/**
 * @Title: UserRoleEnum
 * @Description: 用户角色枚举类
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:40
 */
public enum UserRoleEnum {

    ROLE_USER,

    ROLE_ROOT;

    public static boolean isRoot(String role) {
        if (ROLE_ROOT.name().equalsIgnoreCase(role)) {
            return true;
        }
        return false;
    }

    public static boolean isUser(String role) {
        return !isRoot(role);
    }
}
