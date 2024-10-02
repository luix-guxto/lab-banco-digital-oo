package banco.cliente;

public class TipoException extends RuntimeException {
    public TipoException() {
        super("Tipo de Cliente inválido");
    }
}
