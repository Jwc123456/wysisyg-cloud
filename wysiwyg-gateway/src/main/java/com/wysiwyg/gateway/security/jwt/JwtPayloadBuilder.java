package com.wysiwyg.gateway.security.jwt;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wwcc
 * @date 2022/4/27 21:12
 **/
public class JwtPayloadBuilder {
    private final Map<String, Object> payload = new HashMap<>();
    /**
     * 附加的属性
     */
    private Map<String, String> additional;
    /**
     * jwt签发者
     **/
    private String iss;
    /**
     * jwt所面向的用户
     **/
    private String sub;
    /**
     * 接收jwt的一方
     **/
    private String aud;
    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     **/
    private LocalDateTime exp;
    /**
     * jwt的签发时间
     **/
    private final LocalDateTime iat = LocalDateTime.now();
    /**
     * 权限集
     */
    private Set<String> roles = new HashSet<>();
    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     **/
    private String jti;


    public JwtPayloadBuilder jti(String jti) {
        this.jti = jti;
        return this;
    }

    public JwtPayloadBuilder iss(String iss) {
        this.iss = iss;
        return this;
    }


    public JwtPayloadBuilder sub(String sub) {
        this.sub = sub;
        return this;
    }

    public JwtPayloadBuilder aud(String aud) {
        this.aud = aud;
        return this;
    }


    public JwtPayloadBuilder roles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public JwtPayloadBuilder expDays(int days) {
        Assert.isTrue(days > 0, "jwt expireDate must after now");
        this.exp = this.iat.plusDays(days);
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public String builder() {
        payload.put("iss", this.iss);
        payload.put("sub", this.sub);
        payload.put("aud", this.aud);
        payload.put("exp", String.valueOf(convertToSecondsSinceEpoch(this.exp)));
        payload.put("iat", String.valueOf(convertToSecondsSinceEpoch(this.iat)));
        payload.put("jti", this.jti);

        if (!CollectionUtils.isEmpty(additional)) {
            payload.putAll(additional);
        }
        payload.put("roles", this.roles);
        return JSON.toJSONString(payload);

    }

    public long convertToSecondsSinceEpoch(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).getEpochSecond();
    }


}
