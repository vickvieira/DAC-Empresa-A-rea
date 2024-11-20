package login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Criptografia.CryptoKeyService;
import Mail.MailCredentials;
import Mail.MailCredentialsRepository;
import Criptografia.CryptoUtils;
import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {

    @Autowired
    private CryptoKeyService cryptoKeyService;

    @Autowired
    private MailCredentialsRepository mailCredentialsRepository;

    @Autowired
    private CryptoUtils cryptoUtils;

    public void cadastrarUsuario(UsuarioDTO loginDTO) throws NoSuchAlgorithmException {
        // Verificar se uma chave de criptografia já existe
        if (!cryptoKeyService.exists()) {
            // Se não existir, gera e salva uma nova chave
            cryptoKeyService.generateAndSaveKey("chave-secreta");
        }

        // Obter a chave e criptografar a senha com ela
        String salt = cryptoUtils.generateSalt();
        String hashedPassword = cryptoUtils.hashPassword(loginDTO.getSenha(), salt);

        // Criar a entidade MailCredentials para salvar no banco
        MailCredentials credentials = new MailCredentials();
        credentials.setEmail(loginDTO.getEmail());
        credentials.setSalt(salt);
        credentials.setHashedPassword(hashedPassword);

        // Salvar as credenciais no banco de dados
        mailCredentialsRepository.save(credentials);
    }
}