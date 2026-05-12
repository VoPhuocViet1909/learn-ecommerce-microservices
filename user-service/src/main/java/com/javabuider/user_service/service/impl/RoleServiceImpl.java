package com.javabuider.user_service.service.impl;

import com.javabuider.user_service.entity.Role;
import com.javabuider.user_service.repository.RoleRepository;
import com.javabuider.user_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Override
    public Role createRole(String roleName) {
        return roleRepository.findByName(roleName)
            .orElseGet(() -> roleRepository.save(
                Role.builder()
                    .name(roleName)
                    .build()
            ));
    }
}