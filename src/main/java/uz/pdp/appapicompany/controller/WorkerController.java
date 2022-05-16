package uz.pdp.appapicompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapicompany.entity.Worker;
import uz.pdp.appapicompany.payload.ApiResponse;
import uz.pdp.appapicompany.payload.WorkerDto;
import uz.pdp.appapicompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping("/api/workers")
    public ResponseEntity<List<Worker>> getWorkers() {
        List<Worker> workers = workerService.getWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/api/worker/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Integer id) {
        Worker worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping("/api/worker")
    public ResponseEntity<ApiResponse> addWorker(@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> editWorker(@Valid @RequestBody WorkerDto workerDto, @PathVariable Integer id) {
        ApiResponse apiResponse = workerService.editWorker(workerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
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
