package banco.transacoes;

import banco.GERENCIADOR_CONTAS;
import banco.contas.Conta;

public class PIX {
    public static void transferir(ChavePix chave, Conta origem, double valor) {
        for(Conta c:GERENCIADOR_CONTAS.CONTAS){
            for(ChavePix p: c.getChaves()){
                if(p.equals(chave)){
                    origem.sacarSemTaxa(valor);
                    try{
                        c.depositar(valor);
                        System.out.println("Pix realizado com sucesso!\nDe: "+origem.getCliente().getNome()+" - "+origem.getTipo()+"\nPara: "+c.getCliente().getNome()+" - "+c.getTipo()+"\nValor: "+valor+" BRL");
                    }catch (Exception e){
                        origem.depositar(valor);
                        throw e;
                    }

                }
            }
        }
    }
}
