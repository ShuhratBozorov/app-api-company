package uz.pdp.appapicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "The working name should not be empty!")
    private String name;

    @NotNull(message = "phoneNumber should not be empty!")
    private String phoneNumber;

    private Integer addressId;

    private Integer departmentId;
}
