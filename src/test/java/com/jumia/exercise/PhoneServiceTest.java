package com.jumia.exercise;

import com.jumia.exercise.model.Customer;
import com.jumia.exercise.model.Phone;
import com.jumia.exercise.services.PhoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(PhoneService.class)
public class PhoneServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneService phoneService;

    @Test
    public void getAllPhones() {
        entityManager.persistAndFlush(new Customer(1, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(2, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(3, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(4, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(5, "(256) 775069443"));

        List<Phone> phones = phoneService.getPhones(null, null);
        Assertions.assertEquals(phones.size(), 5);
    }

    @Test
    public void getAllPhonesPaginated() {
        entityManager.persistAndFlush(new Customer(1, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(2, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(3, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(4, "(256) 775069443"));
        entityManager.persistAndFlush(new Customer(5, "(256) 775069443"));

        List<Phone> firstPage = phoneService.getPhones(0, 2);
        Assertions.assertEquals(2, firstPage.size());
        List<Phone> secondPage = phoneService.getPhones(1, 2);
        Assertions.assertEquals(2, secondPage.size());
        List<Phone> thirdPage = phoneService.getPhones(2, 2);
        Assertions.assertEquals(1, thirdPage.size());
        List<Phone> fourthPage = phoneService.getPhones(3, 2);
        Assertions.assertEquals(0, fourthPage.size());
    }

    @Test
    public void getAllPhonesByCountry() {
        entityManager.persistAndFlush(new Customer(1, "(256) 775069443")); // Uganda
        entityManager.persistAndFlush(new Customer(2, "(256) 3142345678")); // Uganda
        entityManager.persistAndFlush(new Customer(3, "(251) 966002259")); // Ethiopia
        entityManager.persistAndFlush(new Customer(4, "(237) 695539786")); // Cameroon
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Cameroon

        List<Phone> phones = phoneService.getPhonesByCountry("Uganda");
        Assertions.assertEquals(2, phones.size());
        Assertions.assertEquals("Uganda", phones.get(0).getCountry());
        Assertions.assertEquals("+256", phones.get(0).getCountryCode());
        Assertions.assertEquals("Uganda", phones.get(1).getCountry());
        Assertions.assertEquals("+256", phones.get(1).getCountryCode());
    }

    @Test
    public void getAllPhonesByCountryPaginated() {
        entityManager.persistAndFlush(new Customer(1, "(256) 775069443")); // Uganda
        entityManager.persistAndFlush(new Customer(2, "(256) 3142345678")); // Uganda
        entityManager.persistAndFlush(new Customer(3, "(251) 966002259")); // Ethiopia
        entityManager.persistAndFlush(new Customer(4, "(237) 695539786")); // Cameroon
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Cameroon

        List<Phone> firstPage = phoneService.getPhonesByCountry("Uganda", 0, 1);
        Assertions.assertEquals(1, firstPage.size());
        Assertions.assertEquals("Uganda", firstPage.get(0).getCountry());
        Assertions.assertEquals("+256", firstPage.get(0).getCountryCode());

        List<Phone> secondPage = phoneService.getPhonesByCountry("Uganda", 1, 1);
        Assertions.assertEquals(1, secondPage.size());
        Assertions.assertEquals("Uganda", secondPage.get(0).getCountry());
        Assertions.assertEquals("+256", secondPage.get(0).getCountryCode());

        List<Phone> thirdPage = phoneService.getPhonesByCountry("Uganda", 2, 1);
        Assertions.assertEquals(0, thirdPage.size());
    }

    @Test
    public void getAllPhonesByValidState() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Valid

        List<Phone> phones = phoneService.getPhonesByState(true);
        Assertions.assertEquals(3, phones.size());
        Assertions.assertTrue(phones.stream().allMatch(Phone::isValid));
    }

    @Test
    public void getAllPhonesByInvalidState() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Valid

        List<Phone> phones = phoneService.getPhonesByState(false);
        Assertions.assertEquals(2, phones.size());
        Assertions.assertTrue(phones.stream().noneMatch(Phone::isValid));
    }

    @Test
    public void getAllPhonesByValidStatePaginated() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Valid

        List<Phone> phones = phoneService.getPhonesByState(true, 0, 2);
        Assertions.assertEquals(2, phones.size());
        Assertions.assertTrue(phones.stream().allMatch(Phone::isValid));

        phones = phoneService.getPhonesByState(true, 1, 2);
        Assertions.assertEquals(1, phones.size());
        Assertions.assertTrue(phones.stream().allMatch(Phone::isValid));

        phones = phoneService.getPhonesByState(true, 2, 2);
        Assertions.assertEquals(0, phones.size());
    }

    @Test
    public void getAllPhonesByCountryAndState() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Morocco Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Mozambique Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Ethiopia Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Cameroon Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Cameroon Valid

        List<Phone> phones = phoneService.getPhonesByCountryAndState("Ethiopia", true);
        Assertions.assertEquals(1, phones.size());
        Assertions.assertTrue(phones.stream().allMatch(Phone::isValid));
    }

    @Test
    public void getAllPhonesByCountryAndStatePaginated() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Morocco Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Mozambique Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Ethiopia Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Cameroon Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Cameroon Valid

        List<Phone> phones = phoneService.getPhonesByCountryAndState("Ethiopia", true, 0, 2);
        Assertions.assertEquals(1, phones.size());
        Assertions.assertTrue(phones.stream().allMatch(Phone::isValid));

        phones = phoneService.getPhonesByCountryAndState("Ethiopia", true, 1, 2);
        Assertions.assertEquals(0, phones.size());
    }

    @Test
    public void getAllPhonesByInvalidStatePaginated() {
        entityManager.persistAndFlush(new Customer(1, "(212) 6546545369")); // Invalid
        entityManager.persistAndFlush(new Customer(2, "(258) 847651504")); // Valid
        entityManager.persistAndFlush(new Customer(3, "(251) 911168450")); // Valid
        entityManager.persistAndFlush(new Customer(4, "(237) 6622284920")); // Invalid
        entityManager.persistAndFlush(new Customer(5, "(237) 696443597")); // Valid

        List<Phone> phones = phoneService.getPhonesByState(false, 0, 2);
        Assertions.assertEquals(2, phones.size());
        Assertions.assertTrue(phones.stream().noneMatch(Phone::isValid));

        phones = phoneService.getPhonesByState(false, 1, 2);
        Assertions.assertEquals(0, phones.size());
    }

    @Test
    public void getValidUgandaPhone() {
        Customer validUganda = new Customer(1, "(256) 775069443");
        entityManager.persistAndFlush(validUganda);

        List<Phone> phones = phoneService.getPhones(null, null);
        Assertions.assertEquals(phones.size(), 1);
        Phone phone = phones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Uganda", phone.getCountry());
        Assertions.assertEquals("+256", phone.getCountryCode());
        Assertions.assertTrue(phone.isValid());
    }

    @Test
    public void getInvalidUgandaPhone() {
        Customer invalidUganda = new Customer(1, "(256) 7503O6263");
        entityManager.persistAndFlush(invalidUganda);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Uganda", phone.getCountry());
        Assertions.assertEquals("+256", phone.getCountryCode());
        Assertions.assertFalse(phone.isValid());
    }

    @Test
    public void getValidMoroccoPhone() {
        Customer validMorocco = new Customer(1, "(212) 698054317");
        entityManager.persistAndFlush(validMorocco);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Morocco", phone.getCountry());
        Assertions.assertEquals("+212", phone.getCountryCode());
        Assertions.assertTrue(phone.isValid());
    }

    @Test
    public void getInvalidMoroccoPhone() {
        Customer validMorocco = new Customer(1, "(212) 6007989253");
        entityManager.persistAndFlush(validMorocco);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Morocco", phone.getCountry());
        Assertions.assertEquals("+212", phone.getCountryCode());
        Assertions.assertFalse(phone.isValid());
    }

    @Test
    public void getValidMozambiquePhone() {
        Customer validMozambique = new Customer(1, "(258) 846565883");
        entityManager.persistAndFlush(validMozambique);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Mozambique", phone.getCountry());
        Assertions.assertEquals("+258", phone.getCountryCode());
        Assertions.assertTrue(phone.isValid());
    }

    @Test
    public void getInvalidMozambiquePhone() {
        Customer validMozambique = new Customer(1, "(258) 84330678235");
        entityManager.persistAndFlush(validMozambique);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Mozambique", phone.getCountry());
        Assertions.assertEquals("+258", phone.getCountryCode());
        Assertions.assertFalse(phone.isValid());
    }

    @Test
    public void getValidEthiopiaPhone() {
        Customer validEthiopia = new Customer(1, "(251) 988200000");
        entityManager.persistAndFlush(validEthiopia);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Ethiopia", phone.getCountry());
        Assertions.assertEquals("+251", phone.getCountryCode());
        Assertions.assertTrue(phone.isValid());
    }

    @Test
    public void getInvalidEthiopiaPhone() {
        Customer validEthiopia = new Customer(1, "(251) 9119454961");
        entityManager.persistAndFlush(validEthiopia);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Ethiopia", phone.getCountry());
        Assertions.assertEquals("+251", phone.getCountryCode());
        Assertions.assertFalse(phone.isValid());
    }

    @Test
    public void getValidCameroonPhone() {
        Customer validCameroon = new Customer(1, "(237) 696443597");
        entityManager.persistAndFlush(validCameroon);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Cameroon", phone.getCountry());
        Assertions.assertEquals("+237", phone.getCountryCode());
        Assertions.assertTrue(phone.isValid());
    }

    @Test
    public void getInvalidCameroonPhone() {
        Customer validCameroon = new Customer(1, "(237) 6622284920");
        entityManager.persistAndFlush(validCameroon);

        List<Phone> allPhones = phoneService.getPhones(null, null);
        Assertions.assertEquals(allPhones.size(), 1);
        Phone phone = allPhones.get(0);
        Assertions.assertNotNull(phone);
        Assertions.assertEquals("Cameroon", phone.getCountry());
        Assertions.assertEquals("+237", phone.getCountryCode());
        Assertions.assertFalse(phone.isValid());
    }
}
