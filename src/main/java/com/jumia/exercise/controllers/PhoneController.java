package com.jumia.exercise.controllers;

import com.jumia.exercise.model.Phone;
import com.jumia.exercise.services.PhoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("list")
    public List<Phone> list(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Boolean state,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (country != null || state != null) {
            return phoneService.getPhonesByCountryAndState(country, state, pageNumber, pageSize);
        } else {
            return phoneService.getPhones(pageNumber, pageSize);
        }
    }
}
