package com.project.fullstack.service;

import com.project.fullstack.common.dto.request.LoginRequest;
import com.project.fullstack.common.dto.response.BaseResponse;
import com.project.fullstack.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    BaseResponse saveUserDetails(User user);

    BaseResponse login(LoginRequest loginRequest);

    UserDetails loadUserByUsername(String email);

}
