package contas;

public class SaldoException extends RuntimeException {
    public SaldoException() {
        super("Saldo insuficiente");
    }
}
