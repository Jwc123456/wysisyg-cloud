package com.wysiwyg.gateway.service;

import com.wysiwyg.common.entity.ContextUserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;


/**
 * @author wwcc
 */
@Service
public class SecurityUserDetailService {

    public Mono<UserDetails> findByUsername(String phoneNo) {
        // TODO 查询数据库操作
        ContextUserInfo contextUserInfo = new ContextUserInfo();
        contextUserInfo.setPhoneNo(phoneNo);
        contextUserInfo.setPassword("111111");
        contextUserInfo.setEmail("1@qq.com");
        contextUserInfo.setRoles(Collections.singleton("admin"));
        return Mono.just(contextUserInfo);
    }
}
