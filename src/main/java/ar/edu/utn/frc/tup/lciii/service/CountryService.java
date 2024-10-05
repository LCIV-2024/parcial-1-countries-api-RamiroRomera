package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.client.CountriesRestClient;
import ar.edu.utn.frc.tup.lciii.dtos.CountriesAmountDTO;
import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        private CountriesRestClient countriesRestClient;

        @Autowired
        private ModelMapper modelMapper;

        public List<CountryDTO> getAllCountries(String name, String code) {
                List<Country> countriesList = countriesRestClient.getAllCountries();
                if (name != null) {
                        for (Country country : countriesList) {
                                if (country.getName().equalsIgnoreCase(name)) {
                                        return List.of(modelMapper.map(country, CountryDTO.class));
                                }
                        }
                }
                if (code != null) {
                        for (Country country : countriesList) {
                                if (country.getCode().equalsIgnoreCase(code)) {
                                        return List.of(modelMapper.map(country, CountryDTO.class));
                                }
                        }
                }

                Type listType = new TypeToken<List<CountryDTO>>(){}.getType();
                return modelMapper.map(countriesList,listType);
        }

        public List<CountryDTO> getAllCountriesByContinent(String continent) {
                List<Country> countriesList = countriesRestClient.getAllCountries();
                List<Country> filterCountries = new ArrayList<>();

                for (Country country : countriesList) {
                        if (country.getRegion().equalsIgnoreCase(continent)) {
                                filterCountries.add(country);
                        }
                }

                Type listType = new TypeToken<List<CountryDTO>>(){}.getType();
                return modelMapper.map(filterCountries,listType);
        }

        public List<CountryDTO> getAllCountriesByLanguage(String language) {
                List<Country> countriesList = countriesRestClient.getAllCountries();
                List<Country> filterCountries = new ArrayList<>();

                for (Country country : countriesList) {
                        if (country.getLanguages() != null) {
                                if (country.getLanguages().containsValue(language)) {
                                        filterCountries.add(country);
                                }
                        }
                }

                Type listType = new TypeToken<List<CountryDTO>>(){}.getType();
                return modelMapper.map(filterCountries,listType);
        }

        public CountryDTO getCountryWithMostFrontiers() {
                List<Country> countriesList = countriesRestClient.getAllCountries();

                Country countryWithMostFrontiers = countriesList.get(0);
                Integer major;
                if (countryWithMostFrontiers.getBorders() == null) {
                        major = 0;
                } else {
                        major = countriesList.get(0).getBorders().size();
                }

                for (Country country : countriesList) {
                        if (country.getBorders() != null) {
                                if (country.getBorders().size() > major) {
                                        major = country.getBorders().size();
                                        countryWithMostFrontiers = country;
                                }
                        }
                }

                return modelMapper.map(countryWithMostFrontiers, CountryDTO.class);
        }


        public List<CountryDTO> saveCountries(CountriesAmountDTO countriesAmountDTO) {
                List<Country> countriesList = countriesRestClient.getAllCountries();
                Collections.shuffle(countriesList);

                List<CountryEntity> countryEntitiesToSave = new ArrayList<>();
                Country countryModelToSave;

                for (int i = 0; i < countriesAmountDTO.getAmountOfCountryToSave(); i++) {
                        countryModelToSave = countriesList.get(i);
                        countryEntitiesToSave.add(modelMapper.map(countryModelToSave, CountryEntity.class));
                }

                List<CountryEntity> countryEntitySaved = countryRepository.saveAll(countryEntitiesToSave);

                Type listType = new TypeToken<List<CountryDTO>>(){}.getType();
                return modelMapper.map(countryEntitySaved,listType);
        }
}