package com.gofield.api.config.security;

import com.gofield.api.config.jwt.JwtSecurityConfig;
import com.gofield.api.config.security.exception.JwtAccessDenyException;
import com.gofield.api.config.security.exception.JwtAuthenticationException;
import com.gofield.api.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenUtil tokenProvider;
    private final JwtAuthenticationException jwtAuthenticationException;
    private final JwtAccessDenyException jwtAccessDenyException;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/css/**",
                "/images/**",
                "/inc/**",
                "/api-docs/**",
                "/js/**",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationException)
                .accessDeniedHandler(jwtAccessDenyException)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/user/v1/term").permitAll()
                .antMatchers("/api/third/**").permitAll()
                .antMatchers("/api/auth/v1/login").permitAll()
                .antMatchers("/api/auth/v1/sample/**").permitAll()
                .antMatchers("/api/auth/v1/refresh").permitAll()
                .antMatchers("/api/search/v1/**").permitAll()

                .anyRequest().authenticated()  // 나머지 API 는 전부 인증 필요

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("https://service.gofield.shop");
        configuration.addAllowedOrigin("http://local.gofield.shop:6060");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}