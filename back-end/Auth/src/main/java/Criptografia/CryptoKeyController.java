package Criptografia;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import Mail.MailCredentials;

@RestController
@RequestMapping("/api/crypto")
public class CryptoKeyController {

    @Autowired
    private CryptoKeyService cryptoKeyService;

    @PostMapping("/generate")
    public CryptoKey generateKey(@RequestParam String input) throws NoSuchAlgorithmException {
        return cryptoKeyService.generateAndSaveKey(input);
    }
    
    @PostMapping("/save-encrypted-credentials")
    public MailCredentials saveEncryptedEmailAndPassword(
            @RequestParam String email,
            @RequestParam String password) throws NoSuchAlgorithmException {
        return cryptoKeyService.saveEncryptedEmailAndPassword(email, password);
    }
    
}
