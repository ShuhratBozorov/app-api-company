package uz.pdp.appapicompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapicompany.entity.Address;
import uz.pdp.appapicompany.payload.AddressDto;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/api/address")
    public List<Address> getAddresses(){
        List<Address> addresses = addressService.getAddresses();
        return addresses;
    }

    @GetMapping("/api/address/{id}")
    public Address getAddress(@PathVariable Integer id){
        Address address = addressService.getAddressById(id);
        return address;
    }

    @PostMapping("/api/address")
    public ApiResponse addAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return apiResponse;
    }

    @PutMapping("/api/address/{id}")
    public ApiResponse editAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Integer id){
        return  addressService.editAddress(addressDto,id);
    }

    @DeleteMapping("/api/address/{id}")
    public ApiResponse deleteAddress(@PathVariable Integer id){
       return addressService.deleteAddress(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
