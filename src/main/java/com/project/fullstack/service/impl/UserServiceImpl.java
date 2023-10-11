package com.project.fullstack.service.impl;

import com.project.fullstack.common.dto.request.LoginRequest;
import com.project.fullstack.common.dto.response.AuthResponse;
import com.project.fullstack.common.dto.response.BaseResponse;
import com.project.fullstack.config.JwtProvider;
import com.project.fullstack.exception.UserServiceException;
import com.project.fullstack.model.User;
import com.project.fullstack.repository.UserRepository;
import com.project.fullstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public BaseResponse saveUserDetails(User user) {
        BaseResponse baseResponse = new BaseResponse();

        if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new UserServiceException("First name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new UserServiceException("Last name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserServiceException("Email cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new UserServiceException("Phone cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserServiceException("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }

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
        return baseResponse;
    }

    @Override
    public BaseResponse login(LoginRequest loginRequest) {
        BaseResponse baseResponse = new BaseResponse();

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        baseResponse.setMessage("Login successful");
        baseResponse.setPayload(new AuthResponse(token));
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserServiceException("User not found with email: "+email, HttpStatus.NOT_FOUND);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        if(userDetails == null){
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
