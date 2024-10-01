package contas;

import cliente.Cliente;
import pref.Preferencias;

public abstract class Conta {
    private double saldo;
    private final String agencia, numero, digito, moeda, tipo;
    private final Cliente cliente;
    private final double taxa, limite;

    public double getTaxa() {
        return taxa;
    }

    public double getLimite() {
        return limite;
    }

    public Conta(String tipo, String moeda, double saldo, Cliente cliente, double taxa, double limite) {
        this.tipo = tipo;
        this.moeda = moeda;
        this.saldo = saldo;
        this.agencia = Preferencias.AGENCIA;
        this.digito = Preferencias.getDigito(tipo);
        this.numero = String.format(Preferencias.FORMATO_CONTA, (int) (Math.random() * Preferencias.TAMANHO_CONTA));
        this.cliente = cliente;
        this.taxa = taxa;
        this.limite = limite;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMoeda() {
        return moeda;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumero() {
        return numero+"-"+digito;
    }

    public String getDadosConta(){
        return "Agência: "+agencia+" Conta: "+numero+"-"+digito;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public String getDigito() {
        return digito;
    }

    public void sacar(double valor) {
        if(saldo >= valor) {
            saldo -= valor;
        }else{
            throw new SaldoException();
        }
    }



}
