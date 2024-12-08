package Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dto.UsuarioDTO;

public interface UsuarioRepository extends MongoRepository<UsuarioDTO, String>{

    public UsuarioDTO findByEmail(String email);

}