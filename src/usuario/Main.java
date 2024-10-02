package usuario;

import banco.GERENCIADOR_CONTAS;
import banco.cliente.Cliente;
import banco.cliente.Fisica;
import banco.contas.CRIAR_CONTA;
import banco.contas.Conta;
import banco.contas.OperationException;
import banco.pref.PREF;
import banco.transacoes.BOLETO;
import banco.transacoes.ChavePix;
import banco.transacoes.PIX;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Cliente eu = new Fisica("Luis", "12345678909", "Rua 1", "12345678", "email@email.com", "1234");

        //(Cliente cliente, String tipo, double saldo, String moeda)
        Conta minhaConta = CRIAR_CONTA.CRIAR_CONTA(eu, PREF.CORRENTE, 1000, PREF.REAL);
        Conta minhaConta2 = CRIAR_CONTA.CRIAR_CONTA(eu, PREF.DIGITAL, 1000, PREF.REAL);
        Conta minhaConta3 = CRIAR_CONTA.CRIAR_CONTA(eu, PREF.INTERNACIONAL, 1000, PREF.DOLAR);

        System.out.println(" _________ PIX _________ ");
        minhaConta.addChavePix(new ChavePix("CPF", eu.getRegistro()));
        minhaConta2.addChavePix(new ChavePix("EMAIL", eu.getEmail()));
        final String cod = "12345678909";
        minhaConta3.addChavePix(new ChavePix("COPIA E COLA", cod));
        PIX.transferir(minhaConta.getChaves().get(0), minhaConta2, 100);
        System.out.println("Saldo atual: "+minhaConta.getSaldo() + " " + minhaConta.getMoeda()+" -- "+minhaConta.getCliente().getNome()+" - "+minhaConta.getTipo());
        System.out.println();

        PIX.transferir(minhaConta2.getChaves().get(0), minhaConta3, 10);
        System.out.println("Saldo atual: "+minhaConta2.getSaldo() + " " + minhaConta2.getMoeda() + " -- "+minhaConta2.getCliente().getNome()+" - "+minhaConta2.getTipo());
        System.out.println();

        try{
            PIX.transferir(minhaConta3.getChaves().get(0), minhaConta, 90);
        }catch (OperationException e){
            System.out.println("Não é possivel realizar PIX para contas internacionais");
        }
        System.out.println("Saldo atual: "+minhaConta3.getSaldo() + " " + minhaConta3.getMoeda() + " -- "+minhaConta3.getCliente().getNome()+" - "+minhaConta3.getTipo());
        System.out.println();

        System.out.println(" _________ BOLETO _________ ");

        //valor aleatorio de 47 caracteres
        String codB = new Random().ints(47, 33, 126).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        BOLETO.pagar(codB, 100, minhaConta);
        System.out.println("Saldo atual: "+minhaConta.getSaldo() + " " + minhaConta.getMoeda()+" -- "+minhaConta.getCliente().getNome()+" - "+minhaConta.getTipo());
        System.out.println();

        BOLETO.pagar(codB, 150, minhaConta2);
        System.out.println("Saldo atual: "+minhaConta2.getSaldo() + " " + minhaConta2.getMoeda()+" -- "+minhaConta2.getCliente().getNome()+" - "+minhaConta2.getTipo());
        System.out.println();

        BOLETO.pagar(codB, 200, minhaConta3);
        System.out.println("Saldo atual: "+minhaConta3.getSaldo() + " " + minhaConta3.getMoeda()+" -- "+minhaConta3.getCliente().getNome()+" - "+minhaConta3.getTipo());
        System.out.println();

        System.out.println(" _________ CONTAS _________ ");
        for(Conta c: GERENCIADOR_CONTAS.CONTAS){
            System.out.print(c.getCliente().getNome() + " - " + c.getTipo());
            System.out.println(" Saldo: "+c.getSaldo()+" "+c.getMoeda());
        }
    }
}
