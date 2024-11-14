package com.br.Clientes;

    import java.io.Serializable;

    public class ClientesDTO implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long id;
        private String nome;
        private String login;
        private String perfil;

        // Construtor padrão
        public ClientesDTO() {}

        // Construtor com parâmetros
        public ClientesDTO(Long id, String nome, String login, String perfil) {
            this.id = id;
            this.nome = nome;
            this.login = login;
            this.perfil = perfil;
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

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPerfil() {
            return perfil;
        }

        public void setPerfil(String perfil) {
            this.perfil = perfil;
        }
    }