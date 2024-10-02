package banco.contas;

import banco.GERENCIADOR_CONTAS;
import banco.cliente.Cliente;
import banco.pref.PREF;

public class CRIAR_CONTA {
    public static Conta CRIAR_CONTA(Cliente cliente, String tipo, double saldo, String moeda){
        // verifica se a conta não existe
        for(Conta c : GERENCIADOR_CONTAS.CONTAS){
            if(c.getCliente().equals(cliente) && c.getTipo().equals(tipo) && c.getMoeda().equals(moeda)){
                throw new IllegalArgumentException("Conta já existente");
            }
        }
        switch (tipo){
            case PREF.CORRENTE:
                GERENCIADOR_CONTAS.CONTAS.add(new Corrente(saldo, cliente));
                break;
            case PREF.DIGITAL:
                GERENCIADOR_CONTAS.CONTAS.add(new Digital(saldo, cliente));
                break;
            case PREF.INTERNACIONAL:
                if(moeda.equals(PREF.DOLAR) || moeda.equals(PREF.EURO)){
                    GERENCIADOR_CONTAS.CONTAS.add(new Internacional(moeda, saldo, cliente));
                } else {
                    throw new IllegalArgumentException("Moeda inválida");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado");
        }
        return GERENCIADOR_CONTAS.CONTAS.get(GERENCIADOR_CONTAS.CONTAS.size()-1);
    }
}
