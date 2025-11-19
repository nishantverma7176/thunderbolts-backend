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

        // ✅ Brevo (Sendinblue) SMTP Configuration
        mailSender.setHost("smtp-relay.brevo.com");
        mailSender.setPort(587);

        // ✅ Replace with your Brevo SMTP username & key
        mailSender.setUsername("9b7db2001@smtp-brevo.com");  // same as the email verified in Brevo
        mailSender.setPassword("xsmtpsib-a6717713ef995e386dc1a836a0f207703004b5b3b96613e7a7b5bda55407b26a-4HBTujDncLFPv84d"); // Brevo SMTP Key (not your account password)

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
