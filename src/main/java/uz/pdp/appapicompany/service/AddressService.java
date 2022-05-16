package uz.pdp.appapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appapicompany.entity.Address;
import uz.pdp.appapicompany.payload.AddressDto;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByHomeNumberAndStreet(addressDto.getHomeNumber(), addressDto.getStreet());
        if (exists) {
            return new ApiResponse("Bunday manzil mavjud", false);
        }
        Address address = new Address();
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);
        return new ApiResponse("Address saved", true);
    }

    public ApiResponse editAddress(AddressDto addressDto, Integer id) {
        boolean exists = addressRepository.existsByHomeNumberAndStreet(addressDto.getHomeNumber(), addressDto.getStreet());
        if (exists) {
            return new ApiResponse("Bunday manzil mavjud", false);
        }

        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday manzil mavjud emas", false);

        Address address = optionalAddress.get();
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);
        return new ApiResponse("Address edited", true);
    }

    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);
        }
    }
}

