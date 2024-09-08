package com.alien.review_my_cv_api.service.user;

import com.alien.review_my_cv_api.entity.User;

public interface UserService {
    boolean existsByEmail(String email);
    void saveUser(User user);
    User getUserByEmail(String email);
}
