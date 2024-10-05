package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.CountriesAmountDTO;
import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDTO;
import ar.edu.utn.frc.tup.lciii.helper.RandomDataForObject;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    private List<CountryDTO> countries;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        countries = new ArrayList<>();
        countries.add((CountryDTO) RandomDataForObject.generateRandomValues(new CountryDTO()));
        countries.add((CountryDTO) RandomDataForObject.generateRandomValues(new CountryDTO()));
        countries.add((CountryDTO) RandomDataForObject.generateRandomValues(new CountryDTO()));
        countries.add((CountryDTO) RandomDataForObject.generateRandomValues(new CountryDTO()));
        countries.add((CountryDTO) RandomDataForObject.generateRandomValues(new CountryDTO()));
    }

    @Test
    void getAllCountriesTest_Code() throws Exception {
        String code = countries.get(0).getCode();
        when(countryService.getAllCountries(null, code)).thenReturn(countries);

        mockMvc.perform(get("/api/countries")
                        .param("code", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void getAllCountriesTest_Name() throws Exception {
        String name = countries.get(0).getName();
        when(countryService.getAllCountries(name, null)).thenReturn(countries);

        mockMvc.perform(get("/api/countries")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void getAllCountriesTest() throws Exception {
        String continent = countries.get(0).getName();
        when(countryService.getAllCountries(null, null)).thenReturn(countries);

        mockMvc.perform(get("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void getCountriesByContinentTest() throws Exception {
        when(countryService.getAllCountriesByContinent("asd")).thenReturn(countries);

        mockMvc.perform(get("/api/countries/asd/continent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void getCountriesByLanguageTest() throws Exception {
        when(countryService.getAllCountriesByLanguage("asd")).thenReturn(countries);

        mockMvc.perform(get("/api/countries/asd/language")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void saveCountriesTest() throws Exception {
        CountriesAmountDTO countriesAmountDTO = new CountriesAmountDTO(3);
        when(countryService.saveCountries(countriesAmountDTO)).thenReturn(countries);

        mockMvc.perform(post("/api/countries")
                        .content(objectMapper.writeValueAsString(countriesAmountDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].code").value(countries.get(0).getCode()))
                .andExpect(jsonPath("$[1].code").value(countries.get(1).getCode()))
                .andExpect(jsonPath("$[2].code").value(countries.get(2).getCode()))
                .andExpect(jsonPath("$[3].code").value(countries.get(3).getCode()))
                .andExpect(jsonPath("$[4].code").value(countries.get(4).getCode()));
    }

    @Test
    void getCountryWithMostFrontiersTest() throws Exception {
        when(countryService.getCountryWithMostFrontiers()).thenReturn(countries.get(0));

        mockMvc.perform(get("/api/countries/most-borders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(countries.get(0).getName()))
                .andExpect(jsonPath("$.code").value(countries.get(0).getCode()));
    }
}
