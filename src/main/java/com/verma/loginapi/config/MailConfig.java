package com.verma.loginapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // ✅ Gmail SMTP Configuration
        mailSender.setHost("smtp.gmail.com");      // Gmail's SMTP server
        mailSender.setPort(587);                   // TLS port

        // ✅ Replace with your Gmail credentials
        mailSender.setUsername("divyatulsi97@gmail.com");        // your Gmail address
        mailSender.setPassword("admhgcxzmxcfydgo");           // 16-character App Password

        // ✅ Mail properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false"); // change to true if you want to see detailed logs

        return mailSender;
    }
}
