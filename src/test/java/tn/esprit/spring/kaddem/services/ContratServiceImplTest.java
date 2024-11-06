package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllContrats() {
        // Arrange
        Contrat contrat1 = new Contrat();
        Contrat contrat2 = new Contrat();
        when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));

        // Act
        List<Contrat> contrats = contratService.retrieveAllContrats();

        // Assert
        assertEquals(2, contrats.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void addContrat() {
        // Arrange
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        // Act
        Contrat result = contratService.addContrat(contrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void retrieveContrat() {
        // Arrange
        int id = 1;
        Contrat contrat = new Contrat();
        when(contratRepository.findById(id)).thenReturn(Optional.of(contrat));

        // Act
        Contrat result = contratService.retrieveContrat(id);

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).findById(id);
    }

    @Test
    void removeContrat() {
        // Arrange
        int id = 1;
        Contrat contrat = new Contrat();
        when(contratRepository.findById(id)).thenReturn(Optional.of(contrat));
        doNothing().when(contratRepository).delete(contrat);

        // Act
        contratService.removeContrat(id);

        // Assert
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void affectContratToEtudiant() {
        // Arrange
        int idContrat = 1;
        String nomE = "Doe";
        String prenomE = "John";

        Etudiant etudiant = new Etudiant();
        etudiant.setNomE(nomE);
        etudiant.setPrenomE(prenomE);
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat();
        contrat.setIdContrat(idContrat);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(etudiantRepository, times(1)).findByNomEAndPrenomE(nomE, prenomE);
        verify(contratRepository, times(1)).save(contrat);
    }
}
