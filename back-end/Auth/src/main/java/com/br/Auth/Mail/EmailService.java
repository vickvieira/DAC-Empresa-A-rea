package com.br.Auth.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException; // Alterado para jakarta
import jakarta.mail.internet.MimeMessage; // Alterado para jakarta

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String corpo) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true); // true para multipart (para anexos)
        
        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(corpo, true); // true para conteúdo HTML

        mailSender.send(mensagem);
    }
}
