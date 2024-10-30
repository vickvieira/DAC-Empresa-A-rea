package Criptografia;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ChaveCrypto")
public class CryptoKey {
    
    @Id
    private String id;
    private String salt;
    private String hashedKey;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHashedKey() {
		return hashedKey;
	}
	public void setHashedKey(String hashedKey) {
		this.hashedKey = hashedKey;
	}

    // Getters and Setters
    
}