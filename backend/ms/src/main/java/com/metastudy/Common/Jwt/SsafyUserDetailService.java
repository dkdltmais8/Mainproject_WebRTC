package com.metastudy.Common.Jwt;

import com.metastudy.Entity.User;
import com.metastudy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SsafyUserDetailService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserId(username);
        if(user != null) {
            SsafyUserDetails userDetails = new SsafyUserDetails(user);
            return userDetails;
        }
        return null;
    }
}
