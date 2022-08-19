package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;

    public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                           UserRepository  userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
    }

    //username, password를 파라미터로 받아서 UsernamePasswordAuthenticationToken 객체를 생성합니다.
    //해당 객체를 통해 authenticate 메소드 로직을 수행합니다. 이때 위에서 만들었던 loadUserByUsername 메소드가 수행되며 유저 정보를 조회해서 인증 정보를 생성하게 됩니다.
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
//
//        UserEmailPasswordAuthenticationToken authenticationToken =
//                new UserEmailPasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
//
//        authenticationToken.setAuthenticated(true);
//        List<User> userList = userRepository.findAll();
//        for (User user : userList) {
//            user.getUserEmail()
//        }
//
//        if()
//
//
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);


        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(new TokenDto(jwt, username), httpHeaders, HttpStatus.OK);
    }
}