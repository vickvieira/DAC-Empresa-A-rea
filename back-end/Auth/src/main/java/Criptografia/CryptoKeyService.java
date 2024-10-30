package Criptografia;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoKeyService {

    @Autowired
    private CryptoKeyRepository cryptoKeyRepository;

    public CryptoKey generateAndSaveKey(String input) throws NoSuchAlgorithmException {
        String salt = CryptoUtils.generateSalt();
        String hashedKey = CryptoUtils.generateHash(input, salt);

        CryptoKey cryptoKey = new CryptoKey();
        cryptoKey.setSalt(salt);
        cryptoKey.setHashedKey(hashedKey);

        return cryptoKeyRepository.save(cryptoKey);
    }
}