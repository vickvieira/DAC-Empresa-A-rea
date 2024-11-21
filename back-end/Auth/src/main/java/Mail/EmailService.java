package Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private MailCredentialsRepository mailCredentialsRepository;

    private JavaMailSender mailSender;

    // Método para inicializar o JavaMailSender com as credenciais do banco de dados
    public void initializeMailSender() {
        MailCredentials credentials = mailCredentialsRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("Nenhuma credencial de email encontrada."));

        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("smtp.seuprovedor.com");  // Ajuste para o host do seu provedor
        mailSenderImpl.setPort(587);  // Ajuste para a porta correta (587 para TLS, 465 para SSL)

        mailSenderImpl.setUsername(credentials.getEmail());
        mailSenderImpl.setPassword(credentials.getHashedPassword());  // Assumindo que a senha foi salva como plain text ou descriptografada

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        this.mailSender = mailSenderImpl;
    }

    // Método para enviar email, usando o mailSender configurado
    public void enviarEmail(String para, String assunto, String corpo) throws MessagingException {
        if (this.mailSender == null) {
            initializeMailSender();  // Inicializa o mailSender se ainda não foi inicializado
        }

        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(corpo, true);

        mailSender.send(mensagem);
    }
}