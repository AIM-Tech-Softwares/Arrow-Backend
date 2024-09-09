package com.aimtech.arrowcore.core.annotation.validator;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.InputMismatchException;

public class CNPJValidator implements ConstraintValidator<ValidCNPJ, String> {

    @Override
    public void initialize(ValidCNPJ validCNPJ) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        try {
            if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                    cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                    cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                    cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                    cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
                    (cnpj.length() != 14)) {
                return(false);
            }

            char digit13, digit14;
            int sm, i, r, num, weight;

            sm = 0;
            weight = 2;
            for (i=11; i>=0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10) {
                    weight = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                digit13 = '0';
            } else {
                digit13 = (char) ((11-r) + 48);
            }

            sm = 0;
            weight = 2;
            for (i=12; i>=0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10) {
                    weight = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                digit14 = '0';
            } else {
                digit14 = (char) ((11-r) + 48);
            }

            return (cnpj.charAt(12) == digit13) && (cnpj.charAt(13) == digit14);

        } catch (InputMismatchException e) {
            return false;
        }

    }
}
