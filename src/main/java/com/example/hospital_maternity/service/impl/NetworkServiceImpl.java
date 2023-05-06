package com.example.hospital_maternity.service.impl;

import com.example.hospital_maternity.model.AdmissionModel;
import com.example.hospital_maternity.model.AllocationModel;
import com.example.hospital_maternity.model.EmployeeModel;
import com.example.hospital_maternity.model.PatientModel;
import com.example.hospital_maternity.service.NetworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class NetworkServiceImpl implements NetworkService {

    private final WebClient webClient;

    @Override
    public List<PatientModel> getAllPatient() {
        return webClient.get()
                .uri("/Patients")
                .retrieve()
                .bodyToFlux(PatientModel.class)
                .collectList()
                .block();
    }

    @Override
    public PatientModel getPatientById(long id) {
        return webClient.get()
                .uri("/Patients/"+ id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .bodyToMono(PatientModel.class)
                .doOnError(ResponseStatusException.class, e -> {
                    if (e.getStatus() == HttpStatus.NOT_FOUND) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id:"+ id);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
                .block();
    }

    @Override
    public List<EmployeeModel> getAllEmployee() {
        return webClient.get()
                .uri("/Employees")
                .retrieve()
                .bodyToFlux(EmployeeModel.class)
                .collectList()
                .block();
    }

    @Override
    public EmployeeModel getEmployeeById(long id) {
        return webClient.get()
                .uri("/Employees/"+ id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .bodyToMono(EmployeeModel.class)
                .doOnError(ResponseStatusException.class, e -> {
                    if (e.getStatus() == HttpStatus.NOT_FOUND) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employees not found with id:"+ id);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
                .block();
    }

    @Override
    public List<AllocationModel> getAllAllocations() {
        return webClient.get()
                .uri("/Allocations")
                .retrieve()
                .bodyToFlux(AllocationModel.class)
                .collectList()
                .block();
    }

    @Override
    public AllocationModel getAllocationById(long id) {
        return webClient.get()
                .uri("/Allocations/"+ id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .bodyToMono(AllocationModel.class)
                .doOnError(ResponseStatusException.class, e -> {
                    if (e.getStatus() == HttpStatus.NOT_FOUND) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Allocation not found with id:"+ id);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
                .block();
    }

    @Override
    public List<AdmissionModel> getAllAdmissionList() {
        return webClient.get()
                .uri("/Admissions")
                .retrieve()
                .bodyToFlux(AdmissionModel.class)
                .collectList()
                .block();
    }

    @Override
    public AdmissionModel getAdmissionById(long id) {
        return webClient.get()
                .uri("/Admissions/"+ id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return clientResponse.createException()
                            .flatMap(e -> Mono.error(new ResponseStatusException(e.getStatusCode(), e.getMessage())));
                })
                .bodyToMono(AdmissionModel.class)
                .doOnError(ResponseStatusException.class, e -> {
                    if (e.getStatus() == HttpStatus.NOT_FOUND) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Admission not found with id:"+ id);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
                .block();
    }

}
