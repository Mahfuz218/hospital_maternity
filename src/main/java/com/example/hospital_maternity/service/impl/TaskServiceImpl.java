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

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    @Override
    public DayOfWeek findBusiestDayOfWeek() {
        List<AdmissionModel> allAdmissionList = networkService.getAllAdmissionList();
        EnumSet<DayOfWeek> dayOfWeeks = EnumSet.allOf(DayOfWeek.class);
        Map<DayOfWeek, Integer> admissionsByDayOfWeek = new HashMap<>();
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            admissionsByDayOfWeek.put(dayOfWeek, 0);
        }

        for (AdmissionModel admission : allAdmissionList) {
            LocalDateTime admissionDateTime = admission.getAdmissionDate();
            DayOfWeek dayOfWeek = admissionDateTime.getDayOfWeek();

            // Increment the count of admissions for the corresponding day of the week
            admissionsByDayOfWeek.put(dayOfWeek, admissionsByDayOfWeek.get(dayOfWeek) + 1);
        }

        // Determine which day of the week has the highest number of admissions
        DayOfWeek busiestDayOfWeek = DayOfWeek.SATURDAY;
        int maxAdmissions = 0;
        for (DayOfWeek dayOfWeek : admissionsByDayOfWeek.keySet()) {
            int admissionsCount = admissionsByDayOfWeek.get(dayOfWeek);
            if (admissionsCount > maxAdmissions) {
                maxAdmissions = admissionsCount;
                busiestDayOfWeek = dayOfWeek;
            }
        }
        return busiestDayOfWeek;
    }

    public List<AllocationModel> getEmployeeAllocation(List<AllocationModel> allocations, long empId) {
        return allocations.stream().filter(allocationModel -> allocationModel.getEmployeeID() == empId)
                .collect(Collectors.toList());
    }

}
