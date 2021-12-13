package com.jumia.exercise.utils;

import java.util.function.Function;

public class CountryPhoneUtils {

    public static String getPhoneCountryName(String phone) {
        return matchPhoneByCountryCodeThenMap(phone, CountryPhone::getCountryName);
    }

    public static String getPhoneCountryCode(String phone) {
        return matchPhoneByCountryCodeThenMap(phone, CountryPhone::getCountryCode);
    }

    private static <T> T matchPhoneByCountryCodeThenMap(String phone, Function<CountryPhone, T> mapFunction) {
        return CountryPhone.stream()
                .filter(country -> country.getCountryCodePattern().matcher(phone).matches())
                .findFirst()
                .map(mapFunction)
                .orElse(null);
    }

    public static boolean isPhoneValid(String phone) {
        return CountryPhone.stream().anyMatch(country -> country.getCountryPhone().matcher(phone).matches());
    }
}
