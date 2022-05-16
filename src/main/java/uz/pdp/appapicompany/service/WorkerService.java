package uz.pdp.appapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appapicompany.entity.Address;
import uz.pdp.appapicompany.entity.Department;
import uz.pdp.appapicompany.entity.Worker;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.payload.WorkerDto;
import uz.pdp.appapicompany.repository.AddressRepository;
import uz.pdp.appapicompany.repository.DepartmentRepository;
import uz.pdp.appapicompany.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getWorkers() {
        List<Worker> workers = workerRepository.findAll();
        return workers;
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public ApiResponse addWorker(WorkerDto workerDto){
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday ishchi mavjud",false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas!",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas!",false);

        Worker worker=new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker saved",true);
    }

    public ApiResponse editWorker(WorkerDto workerDto,Integer id){
        boolean exists = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(),id);
        if (exists)
            return new ApiResponse("Bunday telefon raqamli ishchi mavjud",false);

        Optional<Worker> optionalWorker= workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Worker is not found by this id",false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas!",false);
        Address address = optionalAddress.get();

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas!",false);
        Department department = optionalDepartment.get();

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(address);
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker edited",true);
    }

    public ApiResponse deleteWorker(Integer id){
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error!!!", false);
        }
    }
}
