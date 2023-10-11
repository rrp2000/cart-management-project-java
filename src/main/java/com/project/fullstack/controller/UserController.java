package com.project.fullstack.controller;

import com.project.fullstack.common.dto.request.LoginRequest;
import com.project.fullstack.common.dto.response.AuthResponse;
import com.project.fullstack.common.dto.response.BaseResponse;
import com.project.fullstack.config.JwtProvider;
import com.project.fullstack.exception.UserServiceException;
import com.project.fullstack.model.User;
import com.project.fullstack.repository.UserRepository;
import com.project.fullstack.service.UserService;
import com.project.fullstack.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createUser(
            @Valid @RequestBody User user
    ){
       BaseResponse baseResponse = userService.saveUserDetails(user);
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(
            @RequestBody LoginRequest loginRequest
    ){
       BaseResponse baseResponse = userService.login(loginRequest);
       return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

}
