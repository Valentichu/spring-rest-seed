package com.valentichu.server.security.subject;

import com.valentichu.server.core.domain.Authority;
import com.valentichu.server.core.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetail的Factory
 */
class UserDetailsFactory {
    static UserDetailsImpl create(User user) {
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUserName(),
                user.getUserPwd(),
                authoritiesToGrantedAuthorities(user.getAuthorities())
        );
    }

    private static List<GrantedAuthority> authoritiesToGrantedAuthorities(List<Authority> authorities) {
        List<String> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(authority.getAuthorityName());
        }
        return grantedAuthorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

