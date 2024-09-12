package com.alien.review_my_cv_api.service.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String EMAIL;

    @Override
    public void sendVerificationEmail(String email) {
        // TODO: Generate token, save it to database, and incorporate it into the email content.

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        String emailBody = """
                <div class="container">
                    <h1>Email Verification</h1>
                    <p>Thank you for registering! Please check your email and click on the verification link to activate your account.</p>
                    <a href="#">Verify Email</a>
                    <p>If you did not receive the email, <a href="#">click here</a> to resend.</p>
                </div>
                """;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom(EMAIL);
            messageHelper.setTo(email);
            messageHelper.setSubject("Email Verification");
            messageHelper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("Email verification sent to " + email);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
