package Criptografia;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CryptoKeyRepository extends MongoRepository<CryptoKey, String> {
	
    // Busca o primeiro documento da coleção (MongoDB cria o _id automaticamente)
    @Query(value = "{}", sort = "{ '_id': 1 }")  // Ordena por _id e pega o primeiro documento
    CryptoKey findFirstByOrderById();
}