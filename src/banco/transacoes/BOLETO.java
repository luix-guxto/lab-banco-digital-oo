package banco.transacoes;

import banco.GERENCIADOR_CONTAS;
import banco.contas.Conta;

public class BOLETO {
    public static void pagar(String codigo, double valor, Conta origem) {
        if (codigo.length() != 47) {
            throw new BoletoException("Código de barras inválido");
        }
        origem.sacar(valor);
        System.out.println("Boleto pago com sucesso\nO valor de " + valor + " foi debitado da conta " + origem.getCliente().getNome());
    }
}
