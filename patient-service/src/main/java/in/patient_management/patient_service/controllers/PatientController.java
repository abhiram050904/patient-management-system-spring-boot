package in.patient_management.patient_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.patient_management.patient_service.request.PatientRequest;
import in.patient_management.patient_service.response.PatientResponse;
import in.patient_management.patient_service.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        log.info("REST request to create patient with email: {}", patientRequest.getEmail());
        PatientResponse response = patientService.CreatePatient(patientRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all-patients")
    public ResponseEntity<Iterable<PatientResponse>> getAllPatients() {
        log.info("REST request to get all patients");
        Iterable<PatientResponse> response = patientService.ListAllPatients();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) {
        log.info("REST request to get patient by ID: {}", id);
        PatientResponse response = patientService.GetPatientById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String id, @RequestBody PatientRequest patientRequest) {
        log.info("REST request to update patient with ID: {}", id);
        PatientResponse response = patientService.UpdatePatient(id, patientRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        log.info("REST request to delete patient with ID: {}", id);
        patientService.DeletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
