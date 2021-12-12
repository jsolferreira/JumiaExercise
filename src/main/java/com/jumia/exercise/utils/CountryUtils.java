package com.jumia.exercise.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CountryUtils {

    private static final Pattern CAMEROON_COUNTRY_CODE_PATTERN = Pattern.compile("^\\(237\\) .*");
    private static final Pattern ETHIOPIA_COUNTRY_CODE_PATTERN = Pattern.compile("^\\(251\\) .*");
    private static final Pattern MOROCCO_COUNTRY_CODE_PATTERN = Pattern.compile("^\\(212\\) .*");
    private static final Pattern MOZAMBIQUE_COUNTRY_CODE_PATTERN = Pattern.compile("^\\(258\\) .*");
    private static final Pattern UGANDA_COUNTRY_CODE_PATTERN = Pattern.compile("^\\(256\\) .*");

    private static final Pattern CAMEROON_PATTERN = Pattern.compile("\\(237\\) ?[2368]\\d{7,8}$");
    private static final Pattern ETHIOPIA_PATTERN = Pattern.compile("\\(251\\) ?[1-59]\\d{8}$");
    private static final Pattern MOROCCO_PATTERN = Pattern.compile("\\(212\\) ?[5-9]\\d{8}$");
    private static final Pattern MOZAMBIQUE_PATTERN = Pattern.compile("\\(258\\) ?[28]\\d{7,8}$");
    private static final Pattern UGANDA_PATTERN = Pattern.compile("\\(256\\) ?\\d{9}$");

    private static final List<Pattern> PATTERNS = Arrays.asList(
            CAMEROON_PATTERN,
            ETHIOPIA_PATTERN,
            MOROCCO_PATTERN,
            MOZAMBIQUE_PATTERN,
            UGANDA_PATTERN
    );

    public static String getCountry(String phone) {
        if (CAMEROON_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "Cameroon";
        }
        if (ETHIOPIA_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "Ethiopia";
        }
        if (MOROCCO_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "Morocco";
        }
        if (MOZAMBIQUE_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "Mozambique";
        }
        if (UGANDA_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "Uganda";
        }
        return null;
    }

    public static String getCountryCode(String phone) {
        if (CAMEROON_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "+237";
        }
        if (ETHIOPIA_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "+251";
        }
        if (MOROCCO_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "+212";
        }
        if (MOZAMBIQUE_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "+258";
        }
        if (UGANDA_COUNTRY_CODE_PATTERN.matcher(phone).matches()) {
            return "+256";
        }
        return null;
    }

    public static boolean isPhoneValid(String phone) {
        return PATTERNS
                .stream()
                .anyMatch(pattern -> pattern.matcher(phone).matches());
    }
}
