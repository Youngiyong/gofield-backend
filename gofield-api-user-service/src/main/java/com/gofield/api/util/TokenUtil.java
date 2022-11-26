package com.gofield.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gofield.api.dto.res.TokenResponse;
import com.gofield.common.exception.UnAuthorizedException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class TokenUtil {

    private final Key key;
    private final String AUTH_PREFIX = "Gofield";

    @Value("${secret.gofield.token_key}")
    private String tokenEncryptKey;

    public TokenUtil(@Value("${secret.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveToken(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(AUTH_PREFIX)) {
            return accessToken.substring(8);
        }
        return null;
    }

    public TokenResponse generateToken(com.gofield.api.dto.Authentication authentication,
                                       Long accessValidity,
                                       Long refreshValidity,
                                       Boolean isSign,
                                       String social) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessValidity);
        Date refreshTokenExpireIn = new Date(now + refreshValidity);

        String accessToken = Jwts.builder()
                .setIssuer(authentication.getIssue())
                .setId(EncryptUtils.aes256Encrypt(tokenEncryptKey ,authentication.getUuid()))
                .claim("isSign", isSign)
                .claim("social", social)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = UUID.randomUUID().toString();
        return TokenResponse.of(AUTH_PREFIX, accessToken, refreshToken, accessTokenExpiresIn.getTime(), refreshTokenExpireIn.getTime());
    }

    public String getSocial(String accessToken){
        accessToken = resolveToken(accessToken);
        DecodedJWT jwt = JWT.decode(accessToken);
        Claim resultClaim =  jwt.getClaim("social");
        return resultClaim.asString();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails principal = new User(claims.getId(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
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