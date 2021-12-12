package com.jumia.exercise.services;

import com.jumia.exercise.model.Customer;
import com.jumia.exercise.model.Phone;
import com.jumia.exercise.repository.CustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PhoneService {

    private final CustomerRepository customerRepository;

    public PhoneService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Phone> getPhones(Integer pageNumber, Integer pageSize) {
        return findAllPhones(pageNumber, pageSize);
    }

    public List<Phone> getPhonesByCountry(String country) {
        return getPhonesByCountry(country, null, null);
    }

    public List<Phone> getPhonesByCountry(String country, Integer pageNumber, Integer pageSize) {
        return getPhonesByCountryAndState(country, null, pageNumber, pageSize);
    }

    public List<Phone> getPhonesByState(Boolean state) {
        return getPhonesByState(state, null, null);
    }

    public List<Phone> getPhonesByState(Boolean state, Integer pageNumber, Integer pageSize) {
        return getPhonesByCountryAndState(null, state, pageNumber, pageSize);
    }

    public List<Phone> getPhonesByCountryAndState(String country, Boolean state) {
        return getPhonesByCountryAndState(country, state, null, null);
    }

    public List<Phone> getPhonesByCountryAndState(String country, Boolean state, Integer pageNumber, Integer pageSize) {
        Stream<Phone> stream = findAllPhones(null, null)
                .stream()
                .filter(phone -> filterPhoneByCountryAndState(phone, country, state));

        return applyPagination(stream, pageNumber, pageSize);
    }

    private boolean filterPhoneByCountryAndState(Phone phone, String country, Boolean state) {
        boolean condition = true;

        if (country != null) {
            condition = phone.getCountry().equalsIgnoreCase(country);
        }

        if (state != null) {
            condition = condition && phone.isValid() == state;
        }

        return condition;
    }

    private List<Phone> applyPagination(Stream<Phone> stream, Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageSize == null) {
            return stream.collect(Collectors.toList());
        }

        return stream
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    private List<Phone> findAllPhones(Integer pageNumber, Integer pageSize) {
        List<Customer> customers;

        if (pageSize == null || pageNumber == null) {
            customers = customerRepository.findAll();
        } else {
            customers = customerRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        }

        return transformCustomersIntoPhones(customers);
    }

    private List<Phone> transformCustomersIntoPhones(List<Customer> customers) {
        return customers.stream()
                .map(Phone::new)
                .collect(Collectors.toList());
    }
}
