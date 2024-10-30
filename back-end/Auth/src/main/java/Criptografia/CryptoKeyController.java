package Criptografia;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
public class CryptoKeyController {

    @Autowired
    private CryptoKeyService cryptoKeyService;

    @PostMapping("/generate")
    public CryptoKey generateKey(@RequestParam String input) throws NoSuchAlgorithmException {
        return cryptoKeyService.generateAndSaveKey(input);
    }
}
