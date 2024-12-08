package repository;

import dto.VooDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VooRepository extends JpaRepository<VooDTO, String> {
    // Busca com base nos códigos dos aeroportos
    List<VooDTO> findByAeroportoOrigemCodigoAndAeroportoDestinoCodigo(String origem, String destino);

    // Busca voos futuros
    List<VooDTO> findByAeroportoOrigemCodigoAndAeroportoDestinoCodigoAndDataHoraAfter(
            String origem, String destino, LocalDateTime dataHora);
}