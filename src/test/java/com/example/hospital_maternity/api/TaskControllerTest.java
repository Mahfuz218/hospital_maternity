package com.example.hospital_maternity.api;

import com.example.hospital_maternity.controller.TaskController;
import com.example.hospital_maternity.model.PatientModel;
import com.example.hospital_maternity.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController patientController;

    @Test
    public void testGetAllStuffPatient() {
        // Arrange
        long employeeId = 123;
        List<PatientModel> expectedPatients = new ArrayList<>();
        expectedPatients.add(new PatientModel(1L, "John", "Carter", "2224446666"));
        expectedPatients.add(new PatientModel(2L, "Mary", "Jhonsen", "0923809472389"));
        Mockito.when(taskService.getAllPatientByStuff(employeeId)).thenReturn(expectedPatients);

        // Act
        List<PatientModel> actualPatients = patientController.getAllStuffPatient(employeeId);

        // Assert
        assertEquals(expectedPatients.size(), actualPatients.size());
        assertEquals(expectedPatients.get(0).getId(), actualPatients.get(0).getId());
    }

    @Test
    public void testGetAllDischargedPatientWithin3Days() {
        // Arrange
        List<PatientModel> expectedPatients = new ArrayList<>();
        expectedPatients.add(new PatientModel(1L, "John", "Carter", "2224446666"));
        expectedPatients.add(new PatientModel(2L, "Mary", "Jhonsen", "0923809472389"));
        Mockito.when(taskService.getAllDisChargePatientWithin3Days()).thenReturn(expectedPatients);

        // Actual data
        List<PatientModel> actualPatients = patientController.getAllDischargedPatientWithin3Days();

        // Assert
        assertEquals(expectedPatients.size(), actualPatients.size());
        assertEquals(expectedPatients.get(0).getId(), actualPatients.get(0).getId());
    }

    @Test
    public void testGetBusiestDayOfWeek() {
        // Arrange
        DayOfWeek expectedDayOfWeek = DayOfWeek.MONDAY;
        Mockito.when(taskService.findBusiestDayOfWeek()).thenReturn(expectedDayOfWeek);

        // Actual data
        DayOfWeek actualDayOfWeek = patientController.getBusiestDayOfWeek();

        // Assert
        assertEquals(expectedDayOfWeek, actualDayOfWeek);
    }

    @Test
    public void testGetAveragePatientStayTimeByStuff() {
        // Arrange
        long employeeId = 123;
        long expectedStayTime = 5L; // in hour
        Mockito.when(taskService.averagePatientStayTimeAtStuff(employeeId)).thenReturn(expectedStayTime);

        // Actual data
        Long actualStayTime = patientController.getAveragePatientStayTimeByStuff(employeeId);

        // Assert
        assertEquals(expectedStayTime, actualStayTime.longValue());
    }
}
