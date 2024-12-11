package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dto.FuncionarioDTO;

@Repository
public interface FuncRepository extends JpaRepository<FuncionarioDTO, Long> {
	FuncionarioDTO findByCpf(String cpf);

	FuncionarioDTO findByEmail(String email);
	
}
