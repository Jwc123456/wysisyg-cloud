package com.wysiwyg.gateway.security.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;


/**
 * @author wwcc
 */
@Slf4j
public class JwtTokenGenerator {
    private static final String JWT_EXP_KEY = "exp";
    private JwtPayloadBuilder jwtPayloadBuilder = new JwtPayloadBuilder();
    private JwtProperties jwtProperties;

    private KeyPair keyPair;

    /**
     * Instantiates a new Jwt token generator.
     *
     * @param jwtProperties the jwt properties
     */
    public JwtTokenGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        KeyPairFactory keyPairFactory = new KeyPairFactory();
        this.keyPair = keyPairFactory.create(jwtProperties.getKeyLocation(), jwtProperties.getKeyAlias(), jwtProperties.getKeyPass());
    }


    /**
     * Jwt token pair jwt token pair.
     *
     * @param aud        the aud
     * @param roles      the roles
     * @param additional the additional
     * @return the jwt token pair
     */
    public JwtTokenPair jwtTokenPair(String aud, Set<String> roles, Map<String, String> additional) {
        String accessToken = jwtToken(aud, jwtProperties.getAccessExpDays(), roles, additional);
        String refreshToken = jwtToken(aud, jwtProperties.getRefreshExpDays(), roles, additional);

        JwtTokenPair jwtTokenPair = new JwtTokenPair();
        jwtTokenPair.setAccessToken(accessToken);
        jwtTokenPair.setRefreshToken(refreshToken);
        //TODO  放入缓存
//        userCache.put(aud,jwtTokenPair);
//        userCache.put(aud,jwtTokenPair);

        return jwtTokenPair;
    }


    /**
     * Jwt token string.
     *
     * @param aud        the aud
     * @param exp        the exp
     * @param roles      the roles
     * @param additional the additional
     * @return the string
     */
    private String jwtToken(String aud, int exp, Set<String> roles, Map<String, String> additional) {

        String payload = jwtPayloadBuilder
                .iss(jwtProperties.getIss())
                .sub(jwtProperties.getSub())
                .aud(aud)
                .additional(additional)
                .roles(roles)
                .expDays(exp)
                .builder();

        return Jwts.builder().signWith(keyPair.getPrivate(),Jwts.SIG.RS256).content(payload).compact();
    }


    /**
     * 解码 并校验签名 过期不予解析
     *
     * @param jwtToken the jwt token
     * @return the jwt claims
     */
    public JSONObject decodeAndVerify(String jwtToken) {
        Assert.hasText(jwtToken, "jwt token must not be bank");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();

        Claims payload = Jwts.parser()
                .verifyWith(rsaPublicKey)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

        Date exp = payload.getExpiration();

        if (isExpired(exp)) {
            throw new IllegalStateException("jwt token is expired");
        }

        return new JSONObject(payload);
    }

    /**
     * 判断jwt token是否过期.
     *
     * @param exp the jwt token exp
     * @return the boolean
     */
    private boolean isExpired(Date exp) {
        return LocalDateTime.now().isAfter(DateUtil.toLocalDateTime(exp));
    }

}
