import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class KeyService {

    @Autowired
    private EncryptedKeyRepository encryptedKeyRepository;

    // Função para gerar uma chave criptografada
    public String generateAndSaveKey() {
        try {
            // Gerador de chaves AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // Tamanho da chave (256 bits)
            SecretKey secretKey = keyGen.generateKey();

            // Codificar a chave em Base64 para armazená-la no MongoDB
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Data atual para salvar no banco
            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Criar um objeto EncryptedKey e salvar no MongoDB
            EncryptedKey encryptedKey = new EncryptedKey(encodedKey, currentDate);
            encryptedKeyRepository.save(encryptedKey);

            return "Chave criptografada gerada e salva com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao gerar a chave criptografada!";
        }
    }
}