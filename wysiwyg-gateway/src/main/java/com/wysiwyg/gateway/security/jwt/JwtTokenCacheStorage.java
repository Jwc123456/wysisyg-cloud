package com.wysiwyg.gateway.security.jwt;//package com.wysiwyg.admin.security.jwt;
//
//import com.wysiwyg.common.bean.JwtTokenPair;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//
///**
// * @author wwcc
// * @date 2022/4/27 21:30
// **/
//public class JwtTokenCacheStorage implements JwtTokenStorage {
//    /**
//     * 查看缓存配置文件 ehcache.xml 定义 过期时间与 refresh token 过期一致.
//     */
//    private static final String TOKEN_CACHE = "usrTkn";
//
//
//    @CachePut(value = TOKEN_CACHE, key = "#userId")
//    @Override
//    public JwtTokenPair put(JwtTokenPair jwtTokenPair, String userId) {
//        return jwtTokenPair;
//    }
//
//    @CacheEvict(value = TOKEN_CACHE, key = "#userId")
//    @Override
//    public void expire(String userId) {
//    }
//
//
//    @Cacheable(value = TOKEN_CACHE, key = "#userId")
//    @Override
//    public JwtTokenPair get(String userId) {
//        return null;
//    }
//}
