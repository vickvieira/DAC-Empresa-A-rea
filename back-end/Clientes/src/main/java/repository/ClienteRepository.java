package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DTO.ClientesDTO;

@Repository
public interface ClienteRepository extends JpaRepository<ClientesDTO, Long> {
    ClientesDTO findByCpf(String cpf);

    ClientesDTO findByEmail(String email);

}
