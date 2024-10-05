package ar.edu.utn.frc.tup.lciii.client;

import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountriesRestClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<Country> getAllCountries() {
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
    }

    /**
     * Agregar mapeo de campo cca3 (String)
     * Agregar mapeo campos borders ((List<String>))
     */
    private Country mapToCountry(Map<String, Object> countryData) {
        Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
        return Country.builder()
                .name((String) nameData.get("common"))
                .population(((Number) countryData.get("population")).longValue())
                .area(((Number) countryData.get("area")).doubleValue())
                .region((String) countryData.get("region"))
                .languages((Map<String, String>) countryData.get("languages"))
                .code((String) countryData.get("cca3"))
                .borders((List<String>) countryData.get("borders"))
                .build();
    }

    private CountryDTO mapToDTO(Country country) {
        return new CountryDTO(country.getCode(), country.getName());
    }
}
