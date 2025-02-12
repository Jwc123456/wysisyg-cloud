package com.wysiwyg.common.context;

import com.wysiwyg.common.model.dto.UserDTO;

import java.util.Set;

/**
 * @author wwcc
 * @date 2024/12/17 18:49:49
 */
public class ContextUser {

    // ThreadLocal 用于存储每个线程独立的用户信息
    private static final ThreadLocal<UserDTO> USER_THREAD_LOCAL = ThreadLocal.withInitial(UserDTO::new);

    // 设置用户信息到 ThreadLocal
    public static void setUserInfo(String userId, String username, Set<String> roles) {
        UserDTO userInfo = USER_THREAD_LOCAL.get();
        userInfo.setUserId(userId);
        userInfo.setUsername(username);
        userInfo.setRoles(roles);
    }

    // 获取当前线程的用户信息
    public static UserDTO getUserInfo() {
        return USER_THREAD_LOCAL.get();
    }

    public static String getUserId(){
        return getUserInfo().getUserId();
    }

    // 清理当前线程的用户信息
    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
