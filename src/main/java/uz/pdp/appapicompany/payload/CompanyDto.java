package uz.pdp.appapicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "corpName bo'sh bo'lmasligi kerak!")
    private String corpName;

    @NotNull(message = "directorName bo'sh bo'lmasligi kerak!")
    private String directorName;

    private Integer address_id;
}
