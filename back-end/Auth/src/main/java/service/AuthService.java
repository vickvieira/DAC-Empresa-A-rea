package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Repository.UsuarioRepository;
import constantes.RabbitmqConstantes;
import dto.UserRequisitionDTO;
import dto.UsuarioDTO;

import java.security.SecureRandom;
import password.PasswordUtils;


@Service
public class AuthService {
        
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SagaService sagaService;
    
    public void cadastrarUsuario(UserRequisitionDTO login) throws Exception {
    	
        if (usuarioRepository.findByEmail(login.getEmail()) != null) {
        	System.out.print("O e-mail já está cadastrado.");
            throw new Exception("O e-mail já está cadastrado.");
        }
    	
    	System.out.println("Usuário cadastrado com sucesso: " );
    	SecureRandom random = new SecureRandom();
        int senha = 1000 + random.nextInt(9000);
        String senhaString = Integer.toString(senha);
    	
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(senhaString, salt);
        
    	
    	UsuarioDTO novoUser = new UsuarioDTO(login.getEmail(), hashedPassword, login.getTipo(), salt);
    	System.out.println("Usuário cadastrado com sucesso: " );
        // Salva o objeto no MongoDB
        usuarioRepository.save(novoUser);

        // Opcional: Log ou retorno para verificar o sucesso
        System.out.println("Usuário cadastrado com sucesso: " + novoUser.getEmail());
        sagaService.enviaMensagem(RabbitmqConstantes.FILA_CLIENTE_CADASTRADO, login);
    }
}