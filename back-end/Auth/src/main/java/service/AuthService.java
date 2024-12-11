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
import java.util.List;

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
        System.out.println("AAAAAAAAAAAA");

        if (usuario == null) {
            throw new Exception("E-mail não encontrado.");
        }

        String hashedInputPassword = PasswordUtils.hashPassword(login.getSenha(), usuario.getSalt());
        System.out.println(hashedInputPassword);
        System.out.println(usuario.getSenha());
        if (!hashedInputPassword.equals(usuario.getSenha())) {
            throw new Exception("Senha incorreta.");
        }

        System.out.println("Usuário logado com sucesso: " + usuario.getEmail());
        return new UserRequisitionDTO(usuario.getEmail(), usuario.getTipo(), usuario.getAtivo());
    }
    
    public void cadastrarUsuario(UserRequisitionDTO login) throws Exception {
        System.out.println("Iniciando cadastro de usuário: " + login.getEmail());

        SecureRandom random = new SecureRandom();
        int senha = 1000 + random.nextInt(9000); 
        String senhaString = Integer.toString(senha);

        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(senhaString, salt);

        UsuarioDTO novoUser = new UsuarioDTO(login.getEmail(), hashedPassword, login.getTipo(), salt, true);
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
    
    public List<UsuarioDTO> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    public void inativarCadastro(String email) {
        UsuarioDTO usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o e-mail: " + email);
        }

        if (!usuario.getAtivo()) {
            throw new IllegalArgumentException("Usuário já está inativo.");
        }

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);

        System.out.println("Usuário inativado: " + email);
    }
}