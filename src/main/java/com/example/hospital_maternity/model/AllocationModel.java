
package com.example.hospital_maternity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode
public class AllocationModel {
    private Long admissionID;
    private Long employeeID;
    private String endTime;
    private Long id;
    private String startTime;
}
