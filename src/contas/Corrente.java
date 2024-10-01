package contas;

import cliente.Cliente;
import pref.Preferencias;

public class Corrente extends Conta{




    public Corrente(String moeda, double saldo, Cliente cliente) {
        super(Preferencias.CORRENTE, moeda, saldo, cliente, Preferencias.TAXA_CORRENTE, Preferencias.LIMITE_CORRENTE);
    }
}
