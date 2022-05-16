package uz.pdp.appapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appapicompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsByHomeNumberAndStreet(String homeNumber, String street);
}
