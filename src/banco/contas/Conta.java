package banco.contas;

import banco.GERENCIADOR_CONTAS;
import banco.cliente.Cliente;
import banco.cliente.RegistroException;
import banco.pref.PREF;
import banco.transacoes.ChavePix;
import banco.transacoes.PixException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Conta {
    private double saldo;
    private final String agencia, numero, digito, moeda, tipo;
    private final Cliente cliente;
    private final double taxa, limiteTotal;
    private Date ultimoSaque;
    private double limiteAtual;
    private List<ChavePix> chaves;

    public double getTaxa() {
        return taxa;
    }

    public double getLimite() {
        Date agora = new Date();
        if(!PREF.isSameDay(ultimoSaque, agora))
            limiteAtual = limiteTotal;
        return limiteAtual;
    }

    public Conta(String tipo, String moeda, double saldo, Cliente cliente, double taxa, double limite) {
        this.tipo = tipo;
        this.moeda = moeda;
        this.saldo = saldo;
        this.agencia = PREF.AGENCIA;
        this.digito = PREF.getDigito(tipo);
        this.numero = String.format(PREF.FORMATO_CONTA, (int) (Math.random() * PREF.TAMANHO_CONTA));
        this.cliente = cliente;
        this.taxa = taxa;
        if(cliente.getTipo().equals(PREF.FISICA)) this.limiteTotal = limite;
        else this.limiteTotal = PREF.getLimiteJuridica(tipo);

        this.chaves = new ArrayList<>();

        ultimoSaque = new Date();

        this.limiteAtual = this.limiteTotal;
    }

    public void addChavePix(ChavePix chave){

        //Verifica se a chave PIX já existe
        for(Conta c: GERENCIADOR_CONTAS.CONTAS){
            for(ChavePix p:c.getChaves()){
                if(p.equals(chave)) throw new PixException("Chave ja existente!");
            }
        }

        //verifica se tem chave do mesmo tipo
        for(ChavePix c : chaves){
            if(c.getTipo().equals(chave.getTipo())){
                throw new IllegalArgumentException("Chave do mesmo tipo já cadastrada");
            }
        }
        if(chave.getTipo().equals("REGISTRO")){
            if(cliente.getTipo().equals(PREF.FISICA))
                if(!PREF.isCPF(chave.getCodigo())) throw new RegistroException("CPF inválido");
            else
                if(!PREF.isCNPJ(chave.getCodigo())) throw new RegistroException("CNPJ inválido");
        }
        chaves.add(chave);
    }

    public void removeChavePix(String tipo){
        if(!PREF.isTipoPixValido(tipo)) throw new IllegalArgumentException("Tipo de chave inválido");
        for(ChavePix c : chaves){
            if(c.getTipo().equals(tipo)){
                chaves.remove(c);
                return;
            }
        }
        throw new IllegalArgumentException("Chave não encontrada");
    }

    public List<ChavePix> getChaves(){
        return chaves;
    }

    public void updateChave(ChavePix chave){
        for(ChavePix c : chaves){
            if(c.getTipo().equals(chave.getTipo())){
                c.setCodigo(chave.getCodigo());
                return;
            }
        }
        throw new IllegalArgumentException("Chave não encontrada");
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
        if(valor > getLimite()) throw new LimiteException();
        if(valor > getSaldo()) throw new SaldoException();
        this.ultimoSaque = new Date();
        this.saldo -= valor;
        this.limiteAtual -= valor;
    }

    public void taxa(double valor){
        if(getSaldo() < valor)
            throw new SaldoException();
        this.saldo -= valor;
    }

    public void depositar(double valor, String moeda){
        if(moeda.isEmpty()) throw new NullPointerException(";Moeda não pode ser vazia");
        if (moeda.equals(getMoeda()))
            depositar(valor);
        else {
            double taxa = (valor * PREF.getTaxaConversao(getMoeda())) + (valor * PREF.IOF);
            double total = valor - taxa;
            if (total < 0) throw new IllegalArgumentException("Valor de depósito deve ser positivo");
            depositar(total);
        }
    }

    public void sacarSemTaxa(double valor){
        if(valor > 0 && valor <= getSaldo() && valor <= getLimite()){
            this.saldo -= valor;
            this.limiteAtual -= valor;
        } else if (valor > getLimite()) {
            throw new LimiteException();
        }
        else if (valor < 0) throw new IllegalArgumentException("Valor de saque deve ser positivo");
        else throw new SaldoException();
    }

}
