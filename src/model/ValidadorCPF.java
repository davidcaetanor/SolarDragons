package model;

public class ValidadorCPF {
    public static boolean isCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;
        if (cpf.chars().distinct().count() == 1) return false;

        int soma = 0, resto;
        for (int i = 1; i <= 9; i++)
            soma += (cpf.charAt(i - 1) - '0') * (11 - i);
        resto = (soma * 10) % 11;
        if (resto == 10) resto = 0;
        if (resto != (cpf.charAt(9) - '0')) return false;

        soma = 0;
        for (int i = 1; i <= 10; i++)
            soma += (cpf.charAt(i - 1) - '0') * (12 - i);
        resto = (soma * 10) % 11;
        if (resto == 10) resto = 0;
        return resto == (cpf.charAt(10) - '0');
    }
}
