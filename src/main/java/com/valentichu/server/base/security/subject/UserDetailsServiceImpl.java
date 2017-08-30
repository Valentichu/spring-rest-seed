package com.valentichu.server.base.security.subject;

import com.valentichu.server.core.domain.User;
import com.valentichu.server.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailService的实现，执行鉴权等安全相关代码时会由Spring Security容器自动调用
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper usermapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper usermapper) {
        this.usermapper = usermapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = usermapper.getUser(userName);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with userName '%s'.", userName));
        } else {
            return UserDetailsFactory.create(user);
        }
    }
}
