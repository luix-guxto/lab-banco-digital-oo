import pref.Preferencias;

public class Main {
    public static void main(String[] args) {
        System.out.println(Preferencias.isSenha("test"));
        System.out.println(Preferencias.isSenha("12355"));
        System.out.println(Preferencias.isSenha("1234"));
    }
}
