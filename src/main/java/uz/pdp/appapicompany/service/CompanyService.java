package uz.pdp.appapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appapicompany.entity.Address;
import uz.pdp.appapicompany.entity.Company;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.payload.CompanyDto;
import uz.pdp.appapicompany.repository.AddressRepository;
import uz.pdp.appapicompany.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists)
            return new ApiResponse("Bunday nomli company mavjud", false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddress_id());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas!", false);
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Company added", true);
    }

    public ApiResponse editCompany(CompanyDto companyDto, Integer id) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists)
            return new ApiResponse("Bunday nomli company mavjud", false);

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday company mavjud emas",false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddress_id());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas!", false);

        Address address = optionalAddress.get();

        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);
        }
    }
}
