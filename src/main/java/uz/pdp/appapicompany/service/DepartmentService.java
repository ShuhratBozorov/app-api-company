package uz.pdp.appapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appapicompany.entity.Address;
import uz.pdp.appapicompany.entity.Company;
import uz.pdp.appapicompany.entity.Department;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.payload.CompanyDto;
import uz.pdp.appapicompany.payload.DepartmentDto;
import uz.pdp.appapicompany.repository.AddressRepository;
import uz.pdp.appapicompany.repository.CompanyRepository;
import uz.pdp.appapicompany.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByName(departmentDto.getName());
        if (exists)
            return new ApiResponse("Bunday nomli department mavjud", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday Company mavjud emas!", false);

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department added", true);
    }

    public ApiResponse editDepartment(DepartmentDto departmentDto, Integer id) {
        boolean exists = departmentRepository.existsByName(departmentDto.getName());
        if (exists)
            return new ApiResponse("Bunday nomli department mavjud", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas",false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Bunday Company mavjud emas!", false);
        Company company = optionalCompany.get();

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department edited", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);
        }
    }
}
