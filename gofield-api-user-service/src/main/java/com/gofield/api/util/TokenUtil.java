package com.gofield.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gofield.api.service.auth.dto.Authentication;
import com.gofield.api.service.auth.dto.response.TokenResponse;
import com.gofield.common.exception.UnAuthorizedException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class TokenUtil {

    private final Key key;
    @Value("${secret.gofield.token_key}")
    private String TOKEN_ENCRYPT_KEY;

    public TokenUtil(@Value("${secret.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenResponse generateToken(Authentication authentication,
                                       Long accessValidity,
                                       Long refreshValidity,
                                       Boolean isSign,
                                       String social) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessValidity);
        Date refreshTokenExpireIn = new Date(now + refreshValidity);

        String accessToken = Jwts.builder()
                .setIssuer(authentication.getIssue())
                .setId(EncryptUtils.aes256Encrypt(TOKEN_ENCRYPT_KEY ,authentication.getUuid()))
                .claim("isSign", isSign)
                .claim("social", social)
                .setExpiration(accessTokenExpiresIn)
                .claim("r_exp", Timestamp.valueOf(LocalDateTime.now().plusSeconds(refreshValidity/1000)).getTime()/1000)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = UUID.randomUUID().toString();
        return TokenResponse.of(Constants.AUTH_PREFIX, accessToken, refreshToken, accessTokenExpiresIn.getTime(), refreshTokenExpireIn.getTime());
    }

    public TokenResponse generateRefreshToken(Authentication authentication,
                                              Long accessValidity,
                                              Long refreshTokenExpireIn,
                                              Boolean isSign,
                                              String social) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessValidity);

        String accessToken = Jwts.builder()
                .setIssuer(authentication.getIssue())
                .setId(EncryptUtils.aes256Encrypt(TOKEN_ENCRYPT_KEY ,authentication.getUuid()))
                .claim("isSign", isSign)
                .claim("social", social)
                .setExpiration(accessTokenExpiresIn)
                .claim("r_exp", refreshTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = UUID.randomUUID().toString();
        return TokenResponse.of(Constants.AUTH_PREFIX, accessToken, refreshToken, accessTokenExpiresIn.getTime(), null);
    }


    public String getSocial(String accessToken){
        accessToken = resolveToken(accessToken);
        DecodedJWT jwt = JWT.decode(accessToken);
        Claim resultClaim =  jwt.getClaim("social");
        return resultClaim.asString();
    }


    public String getUserUuid(HttpServletRequest request) {
        String accessToken = request.getHeader(Constants.AUTHORIZATION);
        if (!StringUtils.isBlank(accessToken) && accessToken.startsWith(Constants.AUTH_PREFIX)) {
            String resolveToken = accessToken.substring(8);
            Claims claims = parseClaims(resolveToken);
            return EncryptUtils.aes256Decrypt(TOKEN_ENCRYPT_KEY, claims.getId());
        }
        return null;
    }

    public String resolveToken(String accessToken) {
        if (!StringUtils.isBlank(accessToken) && accessToken.startsWith(Constants.AUTH_PREFIX)) {
            return accessToken.substring(8);
        }
        return null;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new UnAuthorizedException( ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, "잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new UnAuthorizedException(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new UnAuthorizedException(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, "지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new UnAuthorizedException(ErrorCode.E401_UNAUTHORIZED, ErrorAction.NONE, "JWT 토큰이 잘못되었습니다.");
        }
    }
}