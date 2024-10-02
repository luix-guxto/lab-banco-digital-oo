package banco.cliente;

import banco.pref.PREF;

import java.util.ArrayList;
import java.util.List;

public class Juridica extends Cliente{
    private final List<Cliente> socios;

    public Juridica(String nome, String cnpj, String endereco, String telefone, String email, String senha) {
        super(PREF.JURIDICA, nome, cnpj, endereco, telefone, email, senha);
        this.socios = new ArrayList<>();
    }

    public void addSocio(Cliente socio){
        this.socios.add(socio);
    }

    public void removeSocio(Cliente socio){
        this.socios.remove(socio);
    }

    public List<Cliente> getSocios(){
        return this.socios;
    }

    public Cliente getSocio(String cpf){
        if(!PREF.isCPF(cpf)) throw new RegistroException("CPF invalido");
        for(Cliente c : this.socios){
            if(c.getRegistro().equals(cpf)){
                return c;
            }
        }
        throw new SocioException("Socio nao encontrado");
    }

    public void updateSocio(Cliente socio){
        for(int i = 0; i < this.socios.size(); i++){
            if(this.socios.get(i).getRegistro().equals(socio.getRegistro())){
                this.socios.set(i, socio);
                return;
            }
        }
        throw new SocioException("Socio nao encontrado");
    }

}
