package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dto.EstadoReservaDTO;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReservaDTO, Long> {
}