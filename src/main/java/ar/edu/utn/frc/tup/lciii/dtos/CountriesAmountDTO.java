package ar.edu.utn.frc.tup.lciii.dtos;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountriesAmountDTO {
    @Positive
    private Integer amountOfCountryToSave;
}
