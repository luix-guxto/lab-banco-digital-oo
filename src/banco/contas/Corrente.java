package banco.contas;

import banco.cliente.Cliente;
import banco.pref.PREF;

public class Corrente extends Conta{

    public Corrente(double saldo, Cliente cliente) {
        super(PREF.CORRENTE, PREF.REAL, saldo, cliente, PREF.TAXA_CORRENTE, PREF.LIMITE_CORRENTE);
    }

    @Override
    public void sacar(double valor){
        double total = valor + (valor * getTaxa());
        if(total > 0 && total <= getSaldo() && valor <= getLimite()){
            super.sacar(valor);
            super.taxa(valor * getTaxa());
        } else if (valor > getLimite()) {
            throw new LimiteException();
        }
        else if (total < 0) throw new IllegalArgumentException("Valor de saque deve ser positivo");
        else throw new SaldoException();
    }
}
