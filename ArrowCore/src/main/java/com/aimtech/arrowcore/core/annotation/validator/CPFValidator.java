package com.aimtech.arrowcore.core.annotation.validator;

import com.aimtech.arrowcore.core.annotation.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.InputMismatchException;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        try {
            if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                    cpf.equals("22222222222") || cpf.equals("33333333333") ||
                    cpf.equals("44444444444") || cpf.equals("55555555555") ||
                    cpf.equals("66666666666") || cpf.equals("77777777777") ||
                    cpf.equals("88888888888") || cpf.equals("99999999999") ||
                    (cpf.length() != 11)) {
                return false;
            }

            char digit10, digit11;
            int sm, i, r, num, weight;

            sm = 0;
            weight = 10;
            for (i=0; i<9; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                digit10 = '0';
            } else {
                digit10 = (char)(r + 48);
            }

            sm = 0;
            weight = 11;
            for (i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                digit11 = '0';
            } else {
                digit11 = (char)(r + 48);
            }

            return (cpf.charAt(9) == digit10) && (cpf.charAt(10) == digit11);
        } catch (InputMismatchException e) {
            return false;
        }
    }
}
