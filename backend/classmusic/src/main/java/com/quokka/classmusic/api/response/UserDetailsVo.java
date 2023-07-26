package com.quokka.classmusic.api.response;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class UserDetailsVo implements UserDetails {

    private static final String[] ROLES = {"USER", "TEACHER", "ADMIN"};
    private static final String ROLE_PREFIX = "ROLE_";

    private UserVo userVo;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsVo(UserVo userVo) {
        this.userVo = userVo;
        this.authorities = extractRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    public UserVo getUserVo() { return userVo; }
    @Override
    public String getPassword() {
        return userVo.getPassword();
    }

    @Override
    public String getUsername() {
        return userVo.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private Collection<? extends GrantedAuthority> extractRole() {
        List<GrantedAuthority> roles = new ArrayList<>();
        int type = userVo.getType();

        // 0 : 일반 회원, 1 : 강사, 2 : 관리자
        if (type < 0 || type >= ROLES.length) {
            throw new JWTVerificationException("존재하지 않는 회원 타입입니다.");
        }
        roles.add(new SimpleGrantedAuthority(ROLE_PREFIX + ROLES[type]));

        log.debug("UserDetailsVo roles : {}", roles);

        return roles;
    }
}
