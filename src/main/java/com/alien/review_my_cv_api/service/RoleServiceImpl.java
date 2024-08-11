package com.alien.review_my_cv_api.service;

import com.alien.review_my_cv_api.entity.Role;
import com.alien.review_my_cv_api.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Role getRoleByName(String user) {
        return roleRepository
                .findByName(user)
                .orElseThrow(() -> new EntityExistsException("Role USER not initialized"));
    }
}
