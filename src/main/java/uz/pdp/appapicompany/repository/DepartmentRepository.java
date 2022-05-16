package uz.pdp.appapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appapicompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByName(String name);
}
