package repository;

import dto.AeroportoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AeroportoRepository extends JpaRepository<AeroportoDTO, String> {
}
