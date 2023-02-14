/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.common_security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description token管理
 * @since Created in 2021/5/2 002 12:14
 **/
@Component
public class TokenManager {

    private final String TOKEN_SIGNKEY = "BNTang";

    public String createToken(String username) {
        // token 过期时间
        long tokenExpiration = 24 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNKEY).compressWith(CompressionCodecs.GZIP).compact();
    }

    public String getUserFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SIGNKEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}