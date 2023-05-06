package com.example.hospital_maternity.service;

import com.example.hospital_maternity.model.AdmissionModel;
import com.example.hospital_maternity.model.AllocationModel;
import com.example.hospital_maternity.model.EmployeeModel;
import com.example.hospital_maternity.model.PatientModel;

import java.util.List;

public interface NetworkService {
    List<PatientModel> getAllPatient();
    PatientModel getPatientById(long id);

    List<EmployeeModel> getAllEmployee();
    EmployeeModel getEmployeeById(long id);

    List<AllocationModel> getAllAllocations();
    AllocationModel getAllocationById(long id);

    List<AdmissionModel> getAllAdmissionList();
    AdmissionModel getAdmissionById(long id);
}
