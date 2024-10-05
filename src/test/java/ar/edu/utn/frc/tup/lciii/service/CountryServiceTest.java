package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.client.CountriesRestClient;
import ar.edu.utn.frc.tup.lciii.dtos.CountriesAmountDTO;
import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDTO;
import ar.edu.utn.frc.tup.lciii.helper.RandomDataForObject;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryServiceTest {

    @SpyBean
    private CountryRepository countryRepository;

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private CountryService countryService;

    @MockBean
    private CountriesRestClient countriesRestClient;

    private List<Country> countries;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        countries = new ArrayList<>();
        countries.add((Country) RandomDataForObject.generateRandomValues(new Country()));
        countries.add((Country) RandomDataForObject.generateRandomValues(new Country()));
        countries.add((Country) RandomDataForObject.generateRandomValues(new Country()));
        countries.add((Country) RandomDataForObject.generateRandomValues(new Country()));
        countries.add((Country) RandomDataForObject.generateRandomValues(new Country()));
    }

    @Test
    void getAllCountriesTest_Name() {
        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountries(countries.get(0).getName(), null);

        assertEquals(countries.get(0).getName(), result.get(0).getName());
        assertEquals(countries.get(0).getCode(), result.get(0).getCode());
    }

    @Test
    void getAllCountriesTest_Code() {
        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountries(null, countries.get(0).getCode());

        assertEquals(countries.get(0).getCode(), result.get(0).getCode());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
    }

    @Test
    void getAllCountriesTest() {
        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountries(null, null);

        assertEquals(countries.size(), result.size());
        assertEquals(countries.get(0).getCode(), result.get(0).getCode());
        assertEquals(countries.get(1).getCode(), result.get(1).getCode());
        assertEquals(countries.get(2).getCode(), result.get(2).getCode());
        assertEquals(countries.get(3).getCode(), result.get(3).getCode());
        assertEquals(countries.get(4).getCode(), result.get(4).getCode());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
        assertEquals(countries.get(1).getName(), result.get(1).getName());
        assertEquals(countries.get(2).getName(), result.get(2).getName());
        assertEquals(countries.get(3).getName(), result.get(3).getName());
        assertEquals(countries.get(4).getName(), result.get(4).getName());
    }

    @Test
    void getAllCountriesByContinentTest() {
        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountriesByContinent(countries.get(0).getRegion());

        assertEquals(1, result.size());
        assertEquals(countries.get(0).getCode(), result.get(0).getCode());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
    }

    @Test
    void getAllCountriesByLanguageTest() {
        for (int i = 0; i < 3; i++) {
            countries.get(i).getLanguages().put("languages", "asdasdsa");
        }

        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.getAllCountriesByLanguage("asdasdsa");

        assertEquals(3, result.size());
        assertEquals(countries.get(0).getCode(), result.get(0).getCode());
        assertEquals(countries.get(1).getCode(), result.get(1).getCode());
        assertEquals(countries.get(2).getCode(), result.get(2).getCode());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
        assertEquals(countries.get(1).getName(), result.get(1).getName());
        assertEquals(countries.get(2).getName(), result.get(2).getName());
    }

    @Test
    void getCountryWithMostFrontiers() {
        for (int i = 0; i < 10; i++) {
            countries.get(3).getBorders().add("asdsad");
        }
        for (int i = 0; i < 5; i++) {
            countries.get(1).getBorders().add("asdsad");
        }

        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        CountryDTO result = countryService.getCountryWithMostFrontiers();

        assertEquals(countries.get(3).getCode(), result.getCode());
        assertEquals(countries.get(3).getName(), result.getName());
    }

    @Test
    void saveCountriesTest() {
        when(countriesRestClient.getAllCountries()).thenReturn(countries);

        List<CountryDTO> result = countryService.saveCountries(new CountriesAmountDTO(3));

        //verify(countryRepository.saveAll(any()), times(1));
        assertEquals(3, result.size());
    }
}
