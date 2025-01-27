package com.example.demo.Application.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Método para enviar el correo
    public void sendEmail(String to, String subject, String body) throws Exception {
        // Crea el mensaje MIME
        MimeMessage message = javaMailSender.createMimeMessage();

        // Configura el helper con la codificación UTF-8
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Establece los campos del mensaje
        helper.setFrom("andres_a_n@yahoo.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);  // Cambiar a 'true' si es HTML

        // Envía el mensaje
        javaMailSender.send(message);
    }
}
