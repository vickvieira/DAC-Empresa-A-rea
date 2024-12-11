package dto;

import java.io.Serializable;
import jakarta.persistence.*;


@Entity
@Table(name = "clientes")
public class ClientesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100) // Limite de caracteres recomendado
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "telefone", length = 15)
    private String telefone;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "rua_numero", length = 200)
    private String ruaNumero;

    @Column(name = "complemento", length = 200)
    private String complemento;

    @Column(name = "cep", length = 10)
    private String cep;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "milhas")
    private Double milhas;

    // Construtor padrão
    public ClientesDTO() {}

    // Construtor com parâmetros
    public ClientesDTO(String nome, String email, String cpf, String telefone, String tipo, String ruaNumero,
                       String complemento, String cep, String cidade, String estado, Double milhas) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tipo = tipo;
        this.ruaNumero = ruaNumero;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.milhas = milhas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuaNumero() {
        return ruaNumero;
    }

    public void setRuaNumero(String ruaNumero) {
        this.ruaNumero = ruaNumero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMilhas() {
        return milhas;
    }

    public void setMilhas(Double milhas) {
        this.milhas = milhas;
    }
}