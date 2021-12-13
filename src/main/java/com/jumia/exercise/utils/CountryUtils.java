package com.jumia.exercise.utils;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CountryUtils {

    private enum Country {
        CAMEROON("Cameroon", "+237", "\\(237\\) ?[2368]\\d{7,8}$", "^\\(237\\) .*"),
        ETHIOPIA("Ethiopia", "+251", "\\(251\\) ?[1-59]\\d{8}$", "^\\(251\\) .*"),
        MOROCCO("Morocco", "+212", "\\(212\\) ?[5-9]\\d{8}$", "^\\(212\\) .*"),
        MOZAMBIQUE("Mozambique", "+258", "\\(258\\) ?[28]\\d{7,8}$", "^\\(258\\) .*"),
        UGANDA("Uganda", "+256", "\\(256\\) ?\\d{9}$", "^\\(256\\) .*");

        private final String name;
        private final Pattern pattern;
        private final Pattern countryCodePattern;
        private final String code;

        Country(String name, String code, String pattern, String countryCodePattern) {
            this.name = name;
            this.code = code;
            this.pattern = Pattern.compile(pattern);
            this.countryCodePattern = Pattern.compile(countryCodePattern);
        }

        public String getName() {
            return name;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Pattern getCountryCodePattern() {
            return countryCodePattern;
        }

        public String getCode() {
            return code;
        }

        public static Stream<Country> stream() {
            return Stream.of(Country.values());
        }
    }

    public static String getPhoneCountryName(String phone) {
        return matchPhoneByCountryCodeThenMap(phone, Country::getName);
    }

    public static String getPhoneCountryCode(String phone) {
        return matchPhoneByCountryCodeThenMap(phone, Country::getCode);
    }

    private static <T> T matchPhoneByCountryCodeThenMap(String phone, Function<Country, T> mapFunction) {
        return Country.stream()
                .filter(country -> country.getCountryCodePattern().matcher(phone).matches())
                .findFirst()
                .map(mapFunction)
                .orElse(null);
    }

    public static boolean isPhoneValid(String phone) {
        return Country.stream().anyMatch(country -> country.getPattern().matcher(phone).matches());
    }
}
