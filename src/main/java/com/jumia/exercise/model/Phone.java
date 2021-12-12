package com.jumia.exercise.model;

import com.jumia.exercise.utils.CountryUtils;

public class Phone {

    private String number;

    private String country;

    private String countryCode;

    private boolean valid;

    public Phone(Customer customer) {
        this.number = customer.getPhone();

        this.country = CountryUtils.getCountry(number);
        this.countryCode = CountryUtils.getCountryCode(number);
        this.valid = CountryUtils.isPhoneValid(number);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
