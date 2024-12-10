package repository;

import dto.VooDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface VooRepository extends JpaRepository<VooDTO, String> {    
    List<VooDTO> findByAeroportoOrigemAndAeroportoDestino(String aeroportoOrigem, String aeroportoDestino);
    
    List<VooDTO> findByAeroportoOrigemAndAeroportoDestinoAndDataHoraAfter(
            String aeroportoOrigem, String aeroportoDestino, LocalDateTime dataHora);

}