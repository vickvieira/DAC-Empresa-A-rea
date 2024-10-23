import org.springframework.data.mongodb.repository.MongoRepository;

public interface EncryptedKeyRepository extends MongoRepository<EncryptedKey, String> {
}