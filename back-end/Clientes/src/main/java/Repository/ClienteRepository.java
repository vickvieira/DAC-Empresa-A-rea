package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}