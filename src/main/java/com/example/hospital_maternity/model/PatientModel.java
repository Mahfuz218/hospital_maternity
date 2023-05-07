
package com.example.hospital_maternity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientModel {
    private Long id;
    private String forename;
    private String nhsNumber;
    private String surname;
}
