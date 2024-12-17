package com.wysiwyg.common.web.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.wysiwyg.common.constant.AuthConstant;
import com.wysiwyg.common.context.ContextUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wwcc
 * @date 2024/12/16 21:10:08
 */
@Component
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpServletRequest) {
            // 从请求头中获取用户信息
            String userId = httpServletRequest.getHeader(AuthConstant.HEAD_USER_ID);
            String userName = httpServletRequest.getHeader(AuthConstant.HEAD_USER_NAME);
            String roles = httpServletRequest.getHeader(AuthConstant.HEAD_USER_NAME_ROLES);

            if (userId != null && userName != null) {
                Set<String> roleSet = null;
                if(StrUtil.isNotEmpty(roles)){
                    roleSet = new HashSet<>(JSON.parseArray(roles, String.class));
                }
                // 存储到 ThreadLocal
                ContextUser.setUserInfo(userId,userName,roleSet);
            }
        }

        try {
            // 继续请求处理
            chain.doFilter(request, response);
        } finally {
            // 清理 ThreadLocal 避免内存泄漏
            ContextUser.clear();
        }
    }
}
