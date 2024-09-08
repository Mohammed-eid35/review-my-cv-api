package com.alien.review_my_cv_api.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendVerificationEmail(String email) {
        // TODO: Implement email sending logic using SMTP
        System.out.println("Email verification sent to " + email);
    }
}
