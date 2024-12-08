package dto;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "voos")
public class VooDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo_voo", length = 10, nullable = false, unique = true)
    private String codigoVoo; // Código do voo (TADS0000)

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora; // Data e hora do voo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroporto_origem", nullable = false)
    private AeroportoDTO aeroportoOrigem; // Aeroporto de origem

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroporto_destino", nullable = false)
    private AeroportoDTO aeroportoDestino; // Aeroporto de destino

    @Column(name = "valor_passagem", nullable = false)
    private Double valorPassagem; // Valor da passagem em reais

    @Column(name = "quantidade_poltronas_total", nullable = false)
    private Integer quantidadePoltronasTotal; // Quantidade total de poltronas

    @Column(name = "quantidade_poltronas_ocupadas", nullable = false)
    private Integer quantidadePoltronasOcupadas; // Quantidade de poltronas ocupadas

    // Construtor padrão
    public VooDTO() {}

    // Construtor com parâmetros
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
