package com.jumia.exercise.utils;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum CountryPhone {
    CAMEROON("Cameroon", "+237", "\\(237\\) ?[2368]\\d{7,8}$", "^\\(237\\) .*"),
    ETHIOPIA("Ethiopia", "+251", "\\(251\\) ?[1-59]\\d{8}$", "^\\(251\\) .*"),
    MOROCCO("Morocco", "+212", "\\(212\\) ?[5-9]\\d{8}$", "^\\(212\\) .*"),
    MOZAMBIQUE("Mozambique", "+258", "\\(258\\) ?[28]\\d{7,8}$", "^\\(258\\) .*"),
    UGANDA("Uganda", "+256", "\\(256\\) ?\\d{9}$", "^\\(256\\) .*");

    private final String countryName;
    private final String countryCode;
    private final Pattern countryPhone;
    private final Pattern countryCodePattern;

    CountryPhone(String countryName, String countryCode, String countryPhone, String countryCodePattern) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.countryPhone = Pattern.compile(countryPhone);
        this.countryCodePattern = Pattern.compile(countryCodePattern);
    }

    public String getCountryName() {
        return countryName;
    }

    public Pattern getCountryPhone() {
        return countryPhone;
    }

    public Pattern getCountryCodePattern() {
        return countryCodePattern;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public static Stream<CountryPhone> stream() {
        return Stream.of(CountryPhone.values());
    }
}
