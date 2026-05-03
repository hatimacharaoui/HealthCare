package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.DossierMedicalDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.service.DossierMedicalService;
import com.healthcare.medicalsystem.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DossierMedicalServiceTest {

    @Autowired
    private DossierMedicalService dossierService;

    @Autowired
    private PatientService patientService;

    private static Long dossierId;
    private static Long patientId;


    @BeforeAll
    static void recupererIdPatient(@Autowired PatientService patientService) {
        PatientDTO dto = new PatientDTO();
        dto.setNom("Acharaoui");
        dto.setPrenom("hatim");
        dto.setEmail("hatim@gmail.com");
        dto.setTelephone("0612345");
        dto.setDateNaissance(LocalDate.of(1993, 6, 25));
        patientId = patientService.create(dto).getId();
    }



    @Test
    @Order(1)
    void testCreerDossier() {
        DossierMedicalDTO dto = new DossierMedicalDTO();
        dto.setDiagnostic("Fièvre");
        dto.setObservations("Température élevée");
        dto.setPatientId(patientId);

        DossierMedicalDTO resultat = dossierService.create(dto);

        assertNotNull(resultat.getId());
        assertEquals("Fièvre", resultat.getDiagnostic());
        assertEquals("Température élevée", resultat.getObservations());
        assertNotNull(resultat.getDateCreation());
        assertEquals(LocalDate.now(), resultat.getDateCreation());

        dossierId = resultat.getId();
    }



    @Test
    @Order(2)
    void testFindDossierParPatient() {
        DossierMedicalDTO resultat = dossierService.findByPatient(patientId);

        assertNotNull(resultat);
        assertEquals(patientId, resultat.getPatientId());
        assertEquals("Fièvre", resultat.getDiagnostic());
    }



    @Test
    @Order(3)
    void testAjouterDiagnostic() {
        DossierMedicalDTO modifierDiagnostic = dossierService.addDiagnostic(dossierId, "Fatigue générale");

        assertEquals("Fatigue générale", modifierDiagnostic.getDiagnostic());
    }



    @Test
    @Order(4)
    void testAjouterObservations() {
        DossierMedicalDTO modifierObservation = dossierService.addObservations(dossierId, "Patient stable");

        assertEquals("Patient stable", modifierObservation.getObservations());
    }

}