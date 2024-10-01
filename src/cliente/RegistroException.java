package cliente;

public class RegistroException extends RuntimeException {
    public RegistroException() {
        super("O Número de Registro é inválido");
    }
}
