package banco.contas;

public class LimiteException extends RuntimeException {
    public LimiteException() {
        super("Limite excedido");
    }
}
