package com.fitness.tracking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String regex = "^(.+)@(.+)$";
    private static final String regexNumber = "^[0-9,\\-() ]*$";

    public static boolean isValidCharacterForNumber(String value) {
        Pattern pattern = Pattern.compile(regexNumber);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidEmail(String emailValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailValue);
        return matcher.matches();
    }

    public static boolean isMinimumLength(String value, int length) {
        return value.length() >= length;
    }
}
