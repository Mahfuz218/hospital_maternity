package com.example.hospital_maternity.controller;

import com.example.hospital_maternity.model.PatientModel;
import com.example.hospital_maternity.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final TaskService taskService;

    @GetMapping("/stuff-patients")
    public List<PatientModel> getAllStuffPatient(@RequestParam long employeeId) {
        return taskService.getAllPatientByStuff(employeeId);
    }

    @GetMapping("/get-discharged-patients_within3-days")
    public List<PatientModel> getAllDischargedPatientWithin3Days() {
        return taskService.getAllDisChargePatientWithin3Days();
    }



}
