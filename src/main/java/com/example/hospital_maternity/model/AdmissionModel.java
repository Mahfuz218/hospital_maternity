
package com.example.hospital_maternity.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdmissionModel {
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private Long id;
    private Long patientID;
}
