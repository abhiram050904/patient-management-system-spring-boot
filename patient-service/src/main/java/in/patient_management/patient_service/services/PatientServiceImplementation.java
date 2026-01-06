package in.patient_management.patient_service.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import in.patient_management.patient_service.entity.Patient;
import in.patient_management.patient_service.exception.BadRequestException;
import in.patient_management.patient_service.exception.ResourceNotFoundException;
import in.patient_management.patient_service.repository.PaitentRepository;
import in.patient_management.patient_service.request.PatientRequest;
import in.patient_management.patient_service.response.PatientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImplementation implements PatientService {

    private final PaitentRepository patientRepository;


    @Override
    public PatientResponse CreatePatient(PatientRequest patientRequest){
        log.info("Creating patient with email: {}", patientRequest.getEmail());
        
        // Check if patient with email already exists
        Patient existingPatient = patientRepository.findByEmail(patientRequest.getEmail());
        if (existingPatient != null) {
            log.error("Patient already exists with email: {}", patientRequest.getEmail());
            throw new BadRequestException("Patient already exists with email: " + patientRequest.getEmail());
        }
        
        try {
            Patient patient = convertRequestToEntity(patientRequest);
            Patient savedPatient = patientRepository.save(patient);
            log.info("Patient created successfully with ID: {}", savedPatient.getId());
            return convertEntityToResponse(savedPatient);
        } catch (Exception e) {
            log.error("Error creating patient: {}", e.getMessage());
            throw new BadRequestException("Invalid date format. Use YYYY-MM-DD");
        }
    }

    private Patient convertRequestToEntity(PatientRequest patientRequest) {
        return Patient.builder()
                .name(patientRequest.getName())
                .email(patientRequest.getEmail())
                .address(patientRequest.getAddress())
                .dateofBirth(LocalDate.parse(patientRequest.getDateofBirth()))
                .build();
    }

    @Override
    public PatientResponse GetPatientById(String id) {
        log.info("Fetching patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID: {}", id);
                    return new ResourceNotFoundException("Patient", "id", id);
                });
        return convertEntityToResponse(patient);
    }

    private PatientResponse convertEntityToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .dateofBirth(patient.getDateofBirth().toString())
                .build();
    }


    @Override
    public List<PatientResponse> ListAllPatients() {
        log.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        log.info("Found {} patients", patients.size());
        return patients.stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    
    @Override
    public PatientResponse UpdatePatient(String id, PatientRequest patientRequest) {
        log.info("Updating patient with ID: {}", id);
        
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID: {}", id);
                    return new ResourceNotFoundException("Patient", "id", id);
                });
        
        if(patientRequest.getName()!=null){
            existingPatient.setName(patientRequest.getName());
        }

        if(patientRequest.getEmail()!=null){
            existingPatient.setEmail(patientRequest.getEmail());
        }
        
        if(patientRequest.getAddress()!=null){
            existingPatient.setAddress(patientRequest.getAddress());
        }

        if(patientRequest.getDateofBirth()!=null){
            try {
                existingPatient.setDateofBirth(LocalDate.parse(patientRequest.getDateofBirth()));
            } catch (Exception e) {
                log.error("Invalid date format: {}", patientRequest.getDateofBirth());
                throw new BadRequestException("Invalid date format. Use YYYY-MM-DD");
            }
        }

        Patient updatedPatient = patientRepository.save(existingPatient);
        log.info("Patient updated successfully with ID: {}", updatedPatient.getId());
        return convertEntityToResponse(updatedPatient);
    }

    @Override
    public void DeletePatient(String id){
        log.info("Deleting patient with ID: {}", id);
        
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID: {}", id);
                    return new ResourceNotFoundException("Patient", "id", id);
                });
        
        patientRepository.deleteById(id);
        log.info("Patient deleted successfully with ID: {}", id);
    }

}
