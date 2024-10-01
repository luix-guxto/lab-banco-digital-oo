package cliente;

import pref.Preferencias;

public abstract class Cliente {
    private final String tipo;
    private final String nome;
    private final String registro;
    private final String endereco;
    private final String telefone;
    private final String email;
    private final String senha;

    public Cliente(String tipo, String nome, String registro, String endereco, String telefone, String email, String senha) {
        this.tipo = tipo;
        this.nome = nome;
        if(tipo.equals(Preferencias.FISICA)){
            if(Preferencias.isCPF(registro)){
                this.registro = registro;
            }else{
                throw new RegistroException();
            }
        }
        else if(tipo.equals(Preferencias.JURIDICA)) {
            if (Preferencias.isCNPJ(registro)) {
                this.registro = registro;
            } else {
                throw new RegistroException();
            }
        }
        else{
            throw new TipoException();
        }
        this.endereco = endereco;
        this.telefone = telefone;
        if(Preferencias.isEmail(email)){
            this.email = email;
        }else{
            throw new EmailException();
        }
        this.senha = senha;
    }
}
