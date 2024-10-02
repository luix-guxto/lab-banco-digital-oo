package banco.cliente;

import banco.pref.PREF;

public abstract class Cliente {
    private final String tipo;
    private final String nome;
    private final String registro;
    private String endereco;
    private String telefone;
    private String email;
    private String senha;

    public Cliente(String tipo, String nome, String registro, String endereco, String telefone, String email, String senha) {
        this.tipo = tipo;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;

        if(tipo.equals(PREF.FISICA) && !PREF.isCPF(registro)) throw new RegistroException("CPF invalido");
        else if(tipo.equals(PREF.JURIDICA) && !PREF.isCNPJ(registro)) throw new RegistroException("CNPJ invalido");
        else if(!tipo.equals(PREF.FISICA) && !tipo.equals(PREF.JURIDICA)) throw new TipoException();
        this.registro = registro;

        if(!PREF.isEmail(email)) throw new EmailException();
        this.email = email;

        if(!PREF.isSenha(senha)) throw new SenhaException("Formato de senha invalida");
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getRegistro() {
        return registro;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        if(!PREF.isEmail(email)) throw new EmailException();
        this.email = email;
    }

    public void setSenha(String senha) {
        if(!PREF.isSenha(senha)) throw new SenhaException("Formato de senha invalida");
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
