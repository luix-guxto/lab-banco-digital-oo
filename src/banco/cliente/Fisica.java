package banco.cliente;

import banco.pref.PREF;

public class Fisica extends Cliente {

    public Fisica(String nome, String cpf, String endereco, String telefone, String  email, String senha) {
        super(PREF.FISICA, nome, cpf, endereco, telefone, email, senha);
    }
}
