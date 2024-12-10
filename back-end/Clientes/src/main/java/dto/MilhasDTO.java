package DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "milhas")
public class MilhasDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClientesDTO cliente;

    @Column(name = "data_hora_transacao", nullable = false)
    private LocalDateTime dataHoraTransacao;

    @Column(name = "quantidade_milhas", nullable = false)
    private Double quantidadeMilhas;

    @Column(name = "tipo_transacao", nullable = false, length = 10)
    private String tipoTransacao;

    @Column(name = "descricao", length = 255)
    private String descricao;

    public MilhasDTO() {
    }

    public MilhasDTO(ClientesDTO cliente, LocalDateTime dataHoraTransacao, Double quantidadeMilhas,
            String tipoTransacao, String descricao) {
        this.cliente = cliente;
        this.dataHoraTransacao = dataHoraTransacao;
        this.quantidadeMilhas = quantidadeMilhas;
        this.tipoTransacao = tipoTransacao;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientesDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClientesDTO cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(LocalDateTime dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public Double getQuantidadeMilhas() {
        return quantidadeMilhas;
    }

    public void setQuantidadeMilhas(Double quantidadeMilhas) {
        this.quantidadeMilhas = quantidadeMilhas;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}