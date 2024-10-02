package banco.transacoes;

import banco.cliente.RegistroException;
import banco.pref.PREF;

public class ChavePix {
    private final String tipo;
    private String codigo;

    public ChavePix(String tipo, String codigo) {
        if(!PREF.isTipoPixValido(tipo)) throw new IllegalArgumentException("Tipo de chave inválido");
        if (tipo.equals("REGISTRO"))
            if(PREF.isCNPJ(codigo) || PREF.isCPF(codigo)) throw new RegistroException("CPF ou CNPJ inválido");
        if (tipo.equals("EMAIL"))
            if(!PREF.isEmail(codigo)) throw new IllegalArgumentException("Email inválido");

        this.tipo = tipo;
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if(this.tipo.equals("REGISTRO")) throw new RegistroException("Chave do tipo REGISTRO não pode ser alterada");
        this.codigo = codigo;
    }
}
