package com.verma.loginapi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    private static final String FROM_EMAIL = "divyatulsi97@gmail.com";

    // Store OTP temporarily for demo
    private String currentOtp;

    // Generate a random 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        currentOtp = String.valueOf(otp);
        return currentOtp;
    }

    // Send an HTML email with OTP
    public void sendSimpleMessage(String to, String subject, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = """
                    <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f7f9fc;">
                        <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;">
                            <h2 style="color: #0d6efd; text-align: center;">🔐 Password Reset Request</h2>
                            <p style="font-size: 16px; color: #333;">Hello,<br><br>
                            We received a request to reset your password. Please use the OTP below to continue:</p>
                            <p style="font-size: 28px; font-weight: bold; color: #dc3545; text-align: center; margin: 20px 0;">%s</p>
                            <p style="font-size: 14px; color: #666;">This code will expire in 10 minutes.</p>
                            <hr>
                            <p style="font-size: 12px; color: #999;">If you did not request a password reset, please ignore this email.</p>
                        </div>
                    </div>
                    """.formatted(otp);

            helper.setFrom(FROM_EMAIL);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(mimeMessage);
            logger.info("✅ Email sent successfully to {}", to);

        } catch (MessagingException e) {
            logger.error("❌ Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    // Send OTP directly
    public void sendOtpEmail(String to) {
        String otp = generateOtp();
        sendSimpleMessage(to, "Your One-Time Password (OTP)", otp);
    }

    // Verify OTP
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
