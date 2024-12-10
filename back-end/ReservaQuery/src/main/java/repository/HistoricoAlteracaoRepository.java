package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dto.HistoricoAlteracaoDTO;

@Repository
public interface HistoricoAlteracaoRepository extends JpaRepository<HistoricoAlteracaoDTO, Long> {
}