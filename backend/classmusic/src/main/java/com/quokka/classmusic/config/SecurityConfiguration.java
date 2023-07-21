package com.quokka.classmusic.config;

import com.quokka.classmusic.common.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] PERMIT_GET_URLS = {
            "/",
            "/teachers/*",
            "/notices/*",
            "/articles/**",
            "/reviews/*"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // rest api이기에 필요없는 기능 비활성화
        httpSecurity
                .csrf().disable()           // cross origin 필터
                .formLogin().disable();     // form login 기능

        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        // 비회원 이용가능 경로
                        .antMatchers("/auth/**").permitAll()
                        .antMatchers(HttpMethod.GET, PERMIT_GET_URLS).permitAll()
                        // 관리자 권한 필요한 경로
                        .antMatchers(HttpMethod.POST, "/notices").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/notices/*").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/notices/*").hasRole("ADMIN")
                        // 강사 권한 필요한 경로
                        .antMatchers(HttpMethod.PUT, "/teachers/*").hasRole("TEACHER")
                        .antMatchers(HttpMethod.DELETE, "/teachers/*").hasRole("TEACHER")
                        // 회원 권한 필요한 경로
                        .antMatchers("/users/**").hasRole("USER")
                        .anyRequest().authenticated()

                        .and()
                        .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                );

        return httpSecurity.build();
    }

}
