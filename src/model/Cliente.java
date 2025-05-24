package model;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String numero;
    private SimulacaoEnergia simulacao;

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    
    public String getEmail() {
        return email != null ? email : "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome != null ? nome : "";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf != null ? cpf : "";
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogradouro() {
        return logradouro != null ? logradouro : "";
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro != null ? bairro : "";
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade != null ? cidade : "";
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado != null ? estado : "";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero != null ? numero : "";
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public SimulacaoEnergia getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(SimulacaoEnergia simulacao) {
        this.simulacao = simulacao;
    }
}
