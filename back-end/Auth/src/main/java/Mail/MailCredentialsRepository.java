package Mail;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MailCredentialsRepository extends MongoRepository<MailCredentials, String> {
}