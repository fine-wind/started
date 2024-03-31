package com.example.started.auth;

import com.example.started.modules.table.service.TableDbService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Log4j2
@Component
@AllArgsConstructor
public class AuthClientUserDetailsServiceImpl implements UserDetailsService {

    final TableDbService tableDbService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("username", "password", new ArrayList<>());
    }
}
