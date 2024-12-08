package service;

import dto.AeroportoDTO;
import dto.VooDTO;
import org.springframework.stereotype.Service;
import repository.AeroportoRepository;
import repository.VooRepository;
import java.time.LocalDateTime;


import java.util.List;

@Service
public class VooService {

    private final VooRepository vooRepository;
    private final AeroportoRepository aeroportoRepository;

    public VooService(VooRepository vooRepository, AeroportoRepository aeroportoRepository) {
        this.vooRepository = vooRepository;
        this.aeroportoRepository = aeroportoRepository;
    }

    public VooDTO cadastrarVoo(VooDTO vooDTO) {
        AeroportoDTO origem = aeroportoRepository.findById(vooDTO.getAeroportoOrigem())
            .orElseThrow(() -> new IllegalArgumentException("Aeroporto de origem não encontrado"));

        AeroportoDTO destino = aeroportoRepository.findById(vooDTO.getAeroportoDestino())
            .orElseThrow(() -> new IllegalArgumentException("Aeroporto de destino não encontrado"));

        vooDTO.setAeroportoOrigem(origem.getCodigo());
        vooDTO.setAeroportoDestino(destino.getCodigo());

        return vooRepository.save(vooDTO);
    }


    public List<VooDTO> buscarVoos(String aeroportoOrigem, String aeroportoDestino) {
        if (aeroportoOrigem != null && aeroportoDestino != null) {
            return vooRepository.findByAeroportoOrigemCodigoAndAeroportoDestinoCodigo(aeroportoOrigem, aeroportoDestino);
        }
        return vooRepository.findAll();
    }

    public List<VooDTO> buscarVoosFuturos(String aeroportoOrigem, String aeroportoDestino, LocalDateTime dataAtual) {
        return vooRepository.findByAeroportoOrigemCodigoAndAeroportoDestinoCodigoAndDataHoraAfter(
                aeroportoOrigem, aeroportoDestino, dataAtual);
    }

    public AeroportoDTO cadastrarAeroporto(AeroportoDTO aeroportoDTO) {
        return aeroportoRepository.save(aeroportoDTO);
    }

    public List<AeroportoDTO> buscarAeroportos() {
        return aeroportoRepository.findAll();
    }
    
    public VooDTO buscarVooPorCodigo(String codigoVoo) {
        return vooRepository.findById(codigoVoo)
                .orElseThrow(() -> new IllegalArgumentException("Voo não encontrado com código: " + codigoVoo));
    }

    public void atualizarVoo(VooDTO voo) {
        vooRepository.save(voo);
    }
}