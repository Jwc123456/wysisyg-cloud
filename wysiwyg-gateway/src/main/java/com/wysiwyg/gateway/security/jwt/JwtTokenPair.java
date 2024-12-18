package com.wysiwyg.gateway.security.jwt;

import lombok.Data;

import java.io.Serializable;


@Data
public class JwtTokenPair implements Serializable {
    private static final long serialVersionUID = -8518897818107784049L;
    private String accessToken;
    private String refreshToken;
}
