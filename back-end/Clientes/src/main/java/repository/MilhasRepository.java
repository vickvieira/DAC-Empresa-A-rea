package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DTO.ClientesDTO;
import DTO.MilhasDTO;

@Repository
public interface MilhasRepository extends JpaRepository<MilhasDTO, Long> {
	List<MilhasDTO> findByCliente(ClientesDTO cliente);
}
