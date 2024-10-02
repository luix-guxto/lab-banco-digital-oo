package banco.contas;

import banco.cliente.Cliente;
import banco.pref.PREF;

public class Digital extends Conta{


    public Digital(double saldo, Cliente cliente) {
        super(PREF.DIGITAL, PREF.REAL, saldo, cliente, PREF.TAXA_DIGITAL, PREF.LIMITE_DIGITAL);
    }

    @Override
    public void sacar(double valor){
        double taxa = Math.max(valor * getTaxa(), 3.5);
        double total = valor + taxa;
        if(total > 0 && total <= getSaldo() && valor <= getLimite()){
            super.sacar(valor);
            super.taxa(taxa);
        } else if (valor > getLimite()) {
            throw new LimiteException();
        }
        else if (total < 0) throw new IllegalArgumentException("Valor de saque deve ser positivo");
        else throw new SaldoException();
    }
}
