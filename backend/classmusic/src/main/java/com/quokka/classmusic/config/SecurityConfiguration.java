package com.quokka.classmusic.config;

import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.common.auth.JwtAuthenticationFilter;
import com.quokka.classmusic.common.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] PERMIT_GET_URLS = {
            "/",
            "/teachers/*",
            "/notices/*",
            "/articles/**",
            "/reviews/*",
            "/users/*"
    };

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtTokenUtil jwtTokenUtil, UserService userService) throws Exception {

        // rest api이기에 필요없는 기능 비활성화
        httpSecurity
                .csrf().disable()           // cross origin 필터
                .formLogin().disable();     // form login 기능

        // 세션 사용 X
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // exception handling for jwt 추가하기

        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        // 비회원 이용가능 경로
                        .antMatchers("/auth/**").permitAll()
                        .antMatchers("/users/find-id").permitAll()
                        .antMatchers(HttpMethod.GET, PERMIT_GET_URLS).permitAll()
                        // 관리자 권한 필요한 경로
                        .antMatchers(HttpMethod.POST, "/notices").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/notices/*").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/notices/*").hasRole("ADMIN")
                        // 강사 권한 필요한 경로
                        .antMatchers(HttpMethod.PUT, "/teachers/*").hasRole("TEACHER")
                        .antMatchers(HttpMethod.DELETE, "/teachers/*").hasRole("TEACHER")
                        // 회원 권한 필요한 경로
                        .antMatchers(HttpMethod.GET,"/users/*").hasAnyRole("USER", "TEACHER")
                        .antMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("USER", "TEACHER")
                        .antMatchers(HttpMethod.DELETE,"/users/*").hasAnyRole("USER", "TEACHER")
                        .antMatchers(HttpMethod.POST,"/users/**").hasAnyRole("USER", "TEACHER")
                        .anyRequest().authenticated()

                        .and()
                        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, userService), UsernamePasswordAuthenticationFilter.class)
                );

        return httpSecurity.build();
    }

}
