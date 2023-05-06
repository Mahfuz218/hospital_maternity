package com.example.hospital_maternity.service;

import com.example.hospital_maternity.model.PatientModel;

import java.util.List;

public interface TaskService {
    List<PatientModel> getAllPatientByStuff(long employeeId);
    List<PatientModel> getAllDisChargePatientWithin3Days();
}
