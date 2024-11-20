package Criptografia;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Mail.MailCredentials;
import Mail.MailCredentialsRepository;

@Service
public class CryptoKeyService {

    @Autowired
    private CryptoKeyRepository cryptoKeyRepository;

    @Autowired
    private MailCredentialsRepository mailCredentialsRepository;
    
    public CryptoKey generateAndSaveKey(String input) throws NoSuchAlgorithmException {
        String salt = CryptoUtils.generateSalt();
        String hashedKey = CryptoUtils.generateHash(input, salt);

        CryptoKey cryptoKey = new CryptoKey();
        cryptoKey.setSalt(salt);
        cryptoKey.setHashedKey(hashedKey);

        return cryptoKeyRepository.save(cryptoKey);
    }
    
    public CryptoKey getGeneratedKey() {
        return cryptoKeyRepository.findFirstByOrderById();
    }
    
    public boolean exists() {
        return cryptoKeyRepository.count() > 0;
    }
    
    public MailCredentials saveEncryptedEmailAndPassword(String email, String password) throws NoSuchAlgorithmException {
        CryptoKey existingKey = cryptoKeyRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("Chave de criptografia não encontrada."));

        String hashedPassword = CryptoUtils.generateHash(password, existingKey.getSalt());

        MailCredentials mailCredentials = new MailCredentials();
        mailCredentials.setEmail(email);
        mailCredentials.setSalt(existingKey.getSalt());
        mailCredentials.setHashedPassword(hashedPassword);

        // Salva no repositório MailCredentials
        return mailCredentialsRepository.save(mailCredentials);
    }

}