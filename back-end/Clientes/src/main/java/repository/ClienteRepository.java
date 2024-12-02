package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dto.ClientesDTO;

@Repository
public interface ClienteRepository extends JpaRepository<ClientesDTO, Long> {}
