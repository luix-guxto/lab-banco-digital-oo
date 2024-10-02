package banco.contas;

import banco.cliente.Cliente;
import banco.pref.PREF;

public class Internacional extends Conta{

    public Internacional(String moeda, double saldo, Cliente cliente){
        super(PREF.INTERNACIONAL, moeda, saldo, cliente, PREF.getTaxaInternacional(moeda), PREF.LIMITE_INTERNACIONAL);
    }

    @Override
    public void sacar(double valor){
        double taxa = (valor * getTaxa())+(valor* PREF.IOF);
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

    @Override
    public void depositar(double valor){
        throw new OperationException("Para depositar em banco.contas internacionais, precisa-se informar a moeda");
    }
}
