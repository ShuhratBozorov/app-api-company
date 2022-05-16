package uz.pdp.appapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appapicompany.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByCorpName(String corpName);
}
