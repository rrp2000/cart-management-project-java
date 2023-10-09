package com.project.fullstack.controller;

import com.project.fullstack.common.dto.request.LoginRequest;
import com.project.fullstack.common.dto.response.AuthResponse;
import com.project.fullstack.common.dto.response.BaseResponse;
import com.project.fullstack.config.JwtProvider;
import com.project.fullstack.exception.UserServiceException;
import com.project.fullstack.model.User;
import com.project.fullstack.repository.UserRepository;
import com.project.fullstack.service.impl.UserServiceImpl;
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
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createUser(
            @RequestBody User user
    ){
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()){
            throw new UserServiceException("User already exist with email: "+ user.getEmail(), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());

        User savedUser = userRepository.save(user);

        baseResponse.setMessage("signup successful");
        baseResponse.setPayload(savedUser);
        baseResponse.setSuccess(true);
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public BaseResponse login(
            @RequestBody LoginRequest loginRequest
    ){
        BaseResponse baseResponse = new BaseResponse();

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        baseResponse.setMessage("Login successful");
        baseResponse.setPayload(new AuthResponse(token));
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(email);
        if(userDetails == null){
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
