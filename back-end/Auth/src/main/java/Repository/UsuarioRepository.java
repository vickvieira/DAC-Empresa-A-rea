package Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import dto.UsuarioDTO;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioDTO, String> {
    UsuarioDTO findByEmail(String email);
}