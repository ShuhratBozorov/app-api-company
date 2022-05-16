package uz.pdp.appapicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull(message = "street bo'sh bo'lmasligi kerak!")
    public String street;
    @NotNull(message = "homeNumber bo'sh bo'lmasligi kerak!")
    public String homeNumber;
}
