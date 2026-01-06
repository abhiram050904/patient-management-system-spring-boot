package in.patient_management.patient_service.services;

import java.util.List;

import in.patient_management.patient_service.request.PatientRequest;
import in.patient_management.patient_service.response.PatientResponse;

public interface PatientService {

    PatientResponse CreatePatient(PatientRequest patientRequest);

    List<PatientResponse> ListAllPatients();

    PatientResponse UpdatePatient(String id, PatientRequest patientRequest);

    PatientResponse GetPatientById(String id);

    void DeletePatient(String id);

}
