package com.example.app_medical.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.reset-token-expiration-minutes:15}")
    private long expirationMinutes;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetCode(String to, String prenom, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Votre code de réinitialisation");
        message.setText(
                "Bonjour " + (prenom != null ? prenom : "") + ",\n\n"
                        + "Votre code de réinitialisation est : " + code + "\n\n"
                        + "Il est valable " + expirationMinutes + " minutes. "
                        + "Saisissez-le dans l'application avec votre nouveau mot de passe.\n\n"
                        + "Si vous n'êtes pas à l'origine de cette demande, ignorez simplement cet e-mail."
        );
        mailSender.send(message);
    }
}