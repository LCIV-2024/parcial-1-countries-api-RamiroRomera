package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.CountriesAmountDTO;
import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries(@RequestParam(required = false) String code,
                                                            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(countryService.getAllCountries(name, code));
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent) {
        return ResponseEntity.ok(countryService.getAllCountriesByContinent(continent));
    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDTO> getCountryWithMostFrontiers() {
        return ResponseEntity.ok(countryService.getCountryWithMostFrontiers());
    }

    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(countryService.getAllCountriesByLanguage(language));
    }

    @PostMapping("/countries")
    public ResponseEntity<List<CountryDTO>> saveCountries(@RequestBody @Valid CountriesAmountDTO countriesAmountDTO) {
        return ResponseEntity.ok(countryService.saveCountries(countriesAmountDTO));
    }
}