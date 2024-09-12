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
                    <p>Thank you for registering at XJudge. Please click the link below to verify your email:</p>
                    <a href="#">Verify Email</a>
                    <p>If you did not register at CV Reviewer, please ignore this email.</p>
                    <p>Best Regards,</p>
                    <p>The CV Reviewer Team</p>
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
