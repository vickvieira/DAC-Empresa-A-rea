package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dto.ReservaDTO;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaDTO, String> {

    List<ReservaDTO> findByCodigoVoo(String codigoVoo);

    List<ReservaDTO> findByEstadoReservaCodigoEstado(int codigoEstado);

    List<ReservaDTO> findByClienteId(Long clienteId);

}