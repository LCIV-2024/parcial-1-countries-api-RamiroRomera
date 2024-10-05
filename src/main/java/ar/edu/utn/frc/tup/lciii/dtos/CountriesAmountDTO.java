package ar.edu.utn.frc.tup.lciii.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Max(10)
    private Integer amountOfCountryToSave;
}
