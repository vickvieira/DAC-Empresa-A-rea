import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "keys")
public class EncryptedKey {
    
    @Id
    private String id;
    
    private String encryptedKey;
    
    private String createdAt;

    // Construtores, Getters e Setters
    public EncryptedKey(String encryptedKey, String createdAt) {
        this.encryptedKey = encryptedKey;
        this.createdAt = createdAt;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}