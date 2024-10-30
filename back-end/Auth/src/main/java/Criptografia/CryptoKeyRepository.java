package Criptografia;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptoKeyRepository extends MongoRepository<CryptoKey, String> {
}