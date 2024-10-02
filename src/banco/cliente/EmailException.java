package banco.cliente;

public class EmailException extends RuntimeException {
  public EmailException() {
    super("Email inválido");
  }
}
