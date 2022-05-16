package uz.pdp.appapicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "Bo'lim nomi bo'sh bo'lmasligi kerak!")
    private String name;

    private Integer companyId;
}
