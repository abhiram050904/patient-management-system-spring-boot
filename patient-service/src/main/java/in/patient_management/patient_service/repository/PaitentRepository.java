package in.patient_management.patient_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.patient_management.patient_service.entity.Patient;

@Repository
public interface PaitentRepository extends MongoRepository<Patient,String> {
    
    Patient findByEmail(String email);

    Patient findByName(String name);

    Patient findByIdIs(String id);
}
