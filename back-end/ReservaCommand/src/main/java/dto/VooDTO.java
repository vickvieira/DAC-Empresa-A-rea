package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VooDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigoVoo;
    private LocalDateTime dataHora;
    private AeroportoDTO aeroportoOrigem; 
    private AeroportoDTO aeroportoDestino;
    private Double valorPassagem;
    private Integer quantidadePoltronasTotal;
    private Integer quantidadePoltronasOcupadas;
    
    public VooDTO() {}

    public VooDTO(String codigoVoo, LocalDateTime dataHora, AeroportoDTO aeroportoOrigem, AeroportoDTO aeroportoDestino,
                  Double valorPassagem, Integer quantidadePoltronasTotal, Integer quantidadePoltronasOcupadas) {
        this.codigoVoo = codigoVoo;
        this.dataHora = dataHora;
        this.aeroportoOrigem = aeroportoOrigem;
        this.aeroportoDestino = aeroportoDestino;
        this.valorPassagem = valorPassagem;
        this.quantidadePoltronasTotal = quantidadePoltronasTotal;
        this.quantidadePoltronasOcupadas = quantidadePoltronasOcupadas;
    }

    // Getters e Setters
    public String getCodigoVoo() {
        return codigoVoo;
    }

    public void setCodigoVoo(String codigoVoo) {
        this.codigoVoo = codigoVoo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public AeroportoDTO getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public void setAeroportoOrigem(AeroportoDTO aeroportoOrigem) {
        this.aeroportoOrigem = aeroportoOrigem;
    }

    public AeroportoDTO getAeroportoDestino() {
        return aeroportoDestino;
    }

    public void setAeroportoDestino(AeroportoDTO aeroportoDestino) {
        this.aeroportoDestino = aeroportoDestino;
    }

    public Double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(Double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public Integer getQuantidadePoltronasTotal() {
        return quantidadePoltronasTotal;
    }

    public void setQuantidadePoltronasTotal(Integer quantidadePoltronasTotal) {
        this.quantidadePoltronasTotal = quantidadePoltronasTotal;
    }

    public Integer getQuantidadePoltronasOcupadas() {
        return quantidadePoltronasOcupadas;
    }

    public void setQuantidadePoltronasOcupadas(Integer quantidadePoltronasOcupadas) {
        this.quantidadePoltronasOcupadas = quantidadePoltronasOcupadas;
    }
}
