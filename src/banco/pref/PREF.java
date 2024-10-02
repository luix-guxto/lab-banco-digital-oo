package banco.pref;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public final class PREF {
    public static final String AGENCIA = "0001";
    public static final String CORRENTE = "Conta Corrente";
    public static final String DIGITAL = "Conta Digital";
    public static final String INTERNACIONAL = "Conta Internacional";
    public static final String REAL = "BRL";
    public static final String DOLAR = "USD";
    public static final String EURO = "EUR";
    public static final String FORMATO_CONTA = "%08d";
    public static final int TAMANHO_CONTA = 100000000;
    public static final String FISICA = "Pessoa Física";
    public static final String JURIDICA = "Pessoa Jurídica";
    public static final double LIMITE_CORRENTE = 1000;
    public static final double TAXA_CORRENTE = 0.05;
    public static final double LIMITE_DIGITAL = 500;
    public static final double TAXA_DIGITAL = 0.02;
    public static final double LIMITE_INTERNACIONAL = 15000;
    public static final double IOF = 0.06;
    public static final String[] TIPOS_PIX = {"CPF", "CNPJ", "EMAIL", "COPIA E COLA", "TELEFONE"};


    private static final HashMap<String, Integer> DIGITO = new HashMap<String, Integer>() {{
        put(PREF.CORRENTE, 1);
        put(PREF.DIGITAL, 2);
        put(PREF.INTERNACIONAL, 3);
    }};
    public static String getDigito(String tipo) {
        return String.valueOf(DIGITO.get(tipo));
    }



    public static boolean isCNPJ(String cnpj) {
        // limpa todos os caracteres que não sejam números
        cnpj = cnpj.replaceAll("[^0-9]", "");
        // verifica se cnpj é um cnpj válido
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222") || cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555") || cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14)) {
            return false;
        } else {
            char dig13, dig14;
            int sm, i, r, num, peso;
            try {
                sm = 0;
                peso = 2;
                for (i = 11; i >= 0; i--) {
                    num = cnpj.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10) {
                        peso = 2;
                    }
                }
                r = sm % 11;
                if ((r == 0) || (r == 1)) {
                    dig13 = '0';
                } else {
                    dig13 = (char) ((11 - r) + 48);
                }
                sm = 0;
                peso = 2;
                for (i = 12; i >= 0; i--) {
                    num = cnpj.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10) {
                        peso = 2;
                    }
                }
                r = sm % 11;
                if ((r == 0) || (r == 1)) {
                    dig14 = '0';
                } else {
                    dig14 = (char) ((11 - r) + 48);
                }
                return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
            } catch (Exception erro) {
                return false;
            }
        }

    }

    public static boolean isCPF(String cpf) {
        // limpa todos os caracteres que não sejam números
        cpf = cpf.replaceAll("[^0-9]", "");
        // verifica se cpf é um cpf válido
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999") || (cpf.length() != 11)) {
            return false;
        } else {
            char dig10, dig11;
            int sm, i, r, num, peso;
            try {
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    num = cpf.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig10 = '0';
                } else {
                    dig10 = (char) (r + 48);
                }
                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = cpf.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig11 = '0';
                } else {
                    dig11 = (char) (r + 48);
                }
                return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
            } catch (Exception erro) {
                return false;
            }
        }
    }

    public static boolean isEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isSenha(String senha){
        //verifica se a senha tem 4 digitos e é numerica
        return senha.matches("[0-9]{4}");
    }

    public static double getTaxaInternacional(String moeda) {
        switch (moeda){
            case PREF.DOLAR:
                return 0.1;
            case PREF.EURO:
                return 0.2;
            default:
                throw new IllegalArgumentException("Moeda não suportada");
        }
    }

    public static double getLimiteJuridica(String tipo) {
        switch (tipo){
            case PREF.CORRENTE:
                return 10000;
            case PREF.DIGITAL:
                return 5000;
            case PREF.INTERNACIONAL:
                return 100000;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado");
        }
    }

    public static double getTaxaConversao(String moeda) {
        switch (moeda){
            case PREF.DOLAR:
                return 0.2;
            case PREF.EURO:
                return 0.4;
            case PREF.REAL:
                return 0.1;
            default:
                throw new IllegalArgumentException("Moeda não suportada");
        }

    }

    public static boolean isSameDay(Calendar data1, Calendar data2){
        return (data1.get(Calendar.YEAR) == data2.get(Calendar.YEAR) && data1.get(Calendar.MONTH) == data2.get(Calendar.MONTH) && data1.get(Calendar.DAY_OF_MONTH) == data2.get(Calendar.DAY_OF_MONTH));
    }
    public static boolean isSameDay(Date data1, Date data2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(data2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isTipoPixValido(String tipo) {
        return Arrays.asList(PREF.TIPOS_PIX).contains(tipo);
    }
}
