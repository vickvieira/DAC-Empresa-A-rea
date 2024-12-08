package service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Repository.UsuarioRepository;
import constantes.RabbitmqConstantes;
import dto.UserRequisitionDTO;
import dto.UsuarioDTO;
import models.LoginRequisition;

import java.security.SecureRandom;
import password.PasswordUtils;


@Service
public class AuthService {
        
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
	@Autowired
	RabbitTemplate rabbitTemplate;
	
    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);

    }
    
    public UserRequisitionDTO logar(LoginRequisition login) throws Exception {
        UsuarioDTO usuario = usuarioRepository.findByEmail(login.getEmail());

        if (usuario == null) {
            throw new Exception("E-mail não encontrado.");
        }

        String hashedInputPassword = PasswordUtils.hashPassword(login.getSenha(), usuario.getSalt());

        if (!hashedInputPassword.equals(usuario.getSenha())) {
            throw new Exception("Senha incorreta.");
        }

        System.out.println("Usuário logado com sucesso: " + usuario.getEmail());
        return new UserRequisitionDTO(usuario.getEmail(), usuario.getTipo());
    }
    
    public void cadastrarUsuario(UserRequisitionDTO login) throws Exception {
	    System.out.println("Iniciando cadastro de usuário: " + login.getEmail());
	
//	    UsuarioDTO usuarioExistente = usuarioRepository.findByEmail(login.getEmail());
//	    if (usuarioExistente == null) {
//	        System.out.println("Nenhum usuário encontrado para o email: " + login.getEmail());
//	    } else {
//	        System.out.println("Usuário encontrado: " + usuarioExistente.getEmail());
//	    }
	    
	    System.out.println("Usuário não encontrado, prosseguindo com cadastro.");
	
	    SecureRandom random = new SecureRandom();
	    int senha = 1000 + random.nextInt(9000); 
	    String senhaString = Integer.toString(senha);
	
	    String salt = PasswordUtils.generateSalt();
	    String hashedPassword = PasswordUtils.hashPassword(senhaString, salt);
	
	    UsuarioDTO novoUser = new UsuarioDTO(login.getEmail(), hashedPassword, login.getTipo(), salt);
	    usuarioRepository.save(novoUser);
	
	    String assunto = "Bem-vindo ao sistema!";
	    String mensagem = "Olá, " + login.getEmail() + "!\n\n" +
	                      "Sua conta foi criada com sucesso.\n" +
	                      "Sua senha é: " + senhaString + "\n\n" +
	                      "Atenciosamente\n";
	
	    System.out.println("Enviando e-mail para: " + login.getEmail());
	    emailService.enviarEmail(login.getEmail(), assunto, mensagem);
	
	    System.out.println("Usuário cadastrado e e-mail enviado com sucesso: " + novoUser.getEmail());
	}
}