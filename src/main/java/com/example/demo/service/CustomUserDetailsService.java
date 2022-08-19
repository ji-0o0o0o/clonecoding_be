package com.example.demo.service;



import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.entity.User target = userRepository.findbyUserEmail(username).orElse(null);

        if (target == null) {
            userRepository.findbyUserId(username).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않습니다.")
            );
        }
        return userRepository.findbyUserEmail(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 해당 유저를 찾을 수 없습니다."));
    }




    private User createUser(String username, com.example.demo.entity.User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getAuthorityEntity().toString());
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
        return new User(user.getUserName(), user.getPassword(), Collections.singleton(grantedAuthorities));
    }
}