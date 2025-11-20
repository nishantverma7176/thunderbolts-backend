package com.verma.loginapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    // ✅ Must match the Gmail account used in your MailConfig or application.properties
    private static final String FROM_EMAIL = "divyatulsi97@gmail.com";

    // Store OTP temporarily (for demo purpose)
    private String currentOtp;

    // ✅ Generate a random 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        currentOtp = String.valueOf(otp);
        return currentOtp;
    }

    // ✅ Generic method — works with your AuthService (to send any email)
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            logger.info("✅ Email sent successfully to {}", to);
        } catch (Exception e) {
            logger.error("❌ Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    // ✅ Helper method (if you want to send OTP directly)
    public void sendOtpEmail(String to) {
        String otp = generateOtp();
        String subject = "Your One-Time Password (OTP)";
        String text = "Your OTP code is: " + otp + "\n\nThis code is valid for 10 minutes.";

        sendSimpleMessage(to, subject, text);
    }

    // ✅ Verify OTP (simple demo version)
    public boolean verifyOtp(String enteredOtp) {
        boolean result = enteredOtp.equals(currentOtp);
        if (result) {
            logger.info("✅ OTP verified successfully");
        } else {
            logger.warn("❌ Invalid OTP entered");
        }
        return result;
    }
}
