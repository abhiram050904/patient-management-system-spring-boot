package in.patient_management.patient_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {

    private String id;

    private String name;

    private String email;

    private String address;

    private String dateofBirth;
    
}
