package com.javabuider.user_service.service.impl;

import com.javabuider.user_service.common.RoleType;
import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.CreateUserResponse;
import com.javabuider.user_service.entity.Role;
import com.javabuider.user_service.entity.User;
import com.javabuider.user_service.exception.ErrorCode;
import com.javabuider.user_service.exception.UserServiceException;
import com.javabuider.user_service.mapper.UserMapper;
import com.javabuider.user_service.repository.UserRepository;
import com.javabuider.user_service.service.RoleService;
import com.javabuider.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER-SERVICE")
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        // 1. Convert DTO sang Entity
        User user = userMapper.toUser(request);
        
        // 2. Mã hóa password
        user.setPassword(passwordEncoder.encode(request.password()));
        
        // 3. Tạo hoặc lấy role CUSTOMER
        Role role = roleService.createRole(RoleType.CUSTOMER.name());
        
        // 4. Gán role cho user
        user.addRole(role);
        
        // 5. Lưu user vào database
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            log.error("User already exists");
            throw new UserServiceException(ErrorCode.USER_ALREADY_EXISTS);
        }
        
        // 6. Convert Entity sang Response DTO
        return userMapper.toCreateUserResponse(user);
    }
}