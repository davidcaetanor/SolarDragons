package model;

public class Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private boolean isAdmin;

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
    }


    public String getCpf() {
        return cpf != null ? cpf : "";
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome != null ? nome : "";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha != null ? senha : "";
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
