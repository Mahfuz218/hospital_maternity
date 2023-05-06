package com.example.hospital_maternity.service.impl;

import com.example.hospital_maternity.model.AdmissionModel;
import com.example.hospital_maternity.model.AllocationModel;
import com.example.hospital_maternity.model.EmployeeModel;
import com.example.hospital_maternity.model.PatientModel;
import com.example.hospital_maternity.service.NetworkService;
import com.example.hospital_maternity.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final NetworkService networkService;


    /**
     * Getting all of patient list that are added by stuff himself.
     * @param employeeId
     * @return List<PatientModel>
     */
    @Override
    public List<PatientModel> getAllPatientByStuff(long employeeId) {
        //checking the employee id exist or not
        EmployeeModel stuff = networkService.getEmployeeById(employeeId);

        //getting all admission id from allocations by created the stuff/employee
        List<AllocationModel> employeeAllocation = getEmployeeAllocation(networkService.getAllAllocations(), employeeId);

        // getting all admission after then getting all of patient ids from those admissions
        List<Long> patientIds = networkService.getAllAdmissionList()
                .stream().filter(admissionModel -> employeeAllocation.stream()
                        .anyMatch(allocationModel -> Objects.equals(allocationModel.getAdmissionID(), admissionModel.getId())))
                .map(AdmissionModel::getPatientID).toList();

        // getting all of patient by matching their ids
        return networkService.getAllPatient().stream()
                .filter(patientModel -> patientIds.stream().anyMatch(aLong -> Objects.equals(aLong, patientModel.getId())))
                .collect(Collectors.toList());


    }

    @Override
    public List<PatientModel> getAllDisChargePatientWithin3Days() {
        List<Long> patientIds = networkService.getAllAdmissionList()
                .stream().filter(admissionModel -> {
                    long dayDiff = ChronoUnit.DAYS.between(admissionModel.getAdmissionDate(), admissionModel.getDischargeDate());
                    return dayDiff <= 3 && dayDiff >= 0;
                })
                .map(AdmissionModel::getPatientID).toList();
        return networkService.getAllPatient().stream()
                .filter(patientModel -> patientIds.stream().anyMatch(aLong -> Objects.equals(aLong, patientModel.getId())))
                .collect(Collectors.toList());
    }

    public List<AllocationModel> getEmployeeAllocation(List<AllocationModel> allocations, long empId) {
        return allocations.stream().filter(allocationModel -> allocationModel.getEmployeeID() == empId)
                .collect(Collectors.toList());
    }

}
