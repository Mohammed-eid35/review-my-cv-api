package com.alien.review_my_cv_api.service;

import com.alien.review_my_cv_api.entity.Role;

public interface RoleService {
    boolean existsByName(String name);

    Role getRoleByName(String user);
}
