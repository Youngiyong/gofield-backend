package com.gofield.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.model.AppleTokenPayload;
import com.gofield.api.model.response.TokenResponse;
import com.gofield.common.exception.model.InvalidException;
import com.gofield.common.exception.model.UnAuthorizedException;
import com.gofield.common.exception.type.ErrorAction;
import com.gofield.common.exception.type.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.common.utils.RandomUtils;
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
import java.io.IOException;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class TokenUtil {
    private static final String AUTH_PREFIX = "Gofield";
    private final Key key;

    @Value("${spring.config.activate.on-profile}")
    private String ACTIVE_PROFILE;

    @Value("${gofield.token_key}")
    private String tokenEncryptKey;


    public TokenUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveToken(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(AUTH_PREFIX)) {
            return accessToken.substring(8);
        }
        return null;
    }

    public String getUserUuid(String accessToken){
        Claims claims = parseClaims(accessToken);

        return claims.getId();
    }

    public DecodedJWT getDecodeJwt(String accessToken){

        return JWT.decode(accessToken);
    }

    public AppleTokenPayload getAppleUserInfo(String accessToken){
        try {
            ObjectMapper objectMapper = null;
            String payload = accessToken.split("\\.")[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            return objectMapper.readValue(decodedPayload, AppleTokenPayload.class);
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "잘못된 토큰입니다.");
        }
    }


    public TokenResponse generateToken(com.gofield.api.model.Authentication authentication,
                                                  Long accessValidity,
                                                  Long refreshValidity) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessValidity);
        Date refreshTokenExpireIn = new Date(now + refreshValidity);

        String accessToken = Jwts.builder()
                .setIssuer(authentication.getIssue())
                .claim("gti", RandomUtils.makeRandomCode(32))
                .setId(EncryptUtils.aes256Encrypt(tokenEncryptKey ,authentication.getUuid()))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        return TokenResponse.builder()
                .grantType(AUTH_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpireIn.getTime())
                .build();
    }


    public String getEncKey(String accessToken){
        accessToken = resolveToken(accessToken);
        DecodedJWT jwt = JWT.decode(accessToken);

        Claim resultClaim =  jwt.getClaim("gti");

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