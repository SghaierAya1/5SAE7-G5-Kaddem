package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllDepartements() {
        // Arrange
        Departement dep1 = new Departement("Informatique");
        Departement dep2 = new Departement("Mathematiques");
        when(departementRepository.findAll()).thenReturn(Arrays.asList(dep1, dep2));

        // Act
        List<Departement> departements = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(2, departements.size());
        assertEquals("Informatique", departements.get(0).getNomDepart());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void addDepartement() {
        // Arrange
        Departement dep = new Departement("Physique");
        when(departementRepository.save(dep)).thenReturn(dep);

        // Act
        Departement result = departementService.addDepartement(dep);

        // Assert
        assertNotNull(result);
        assertEquals("Physique", result.getNomDepart());
        verify(departementRepository, times(1)).save(dep);
    }

    @Test
    void updateDepartement() {
        // Arrange
        Departement dep = new Departement(1, "Chimie");
        when(departementRepository.save(dep)).thenReturn(dep);

        // Act
        Departement result = departementService.updateDepartement(dep);

        // Assert
        assertNotNull(result);
        assertEquals("Chimie", result.getNomDepart());
        verify(departementRepository, times(1)).save(dep);
    }

    @Test
    void retrieveDepartement() {
        // Arrange
        int id = 1;
        Departement dep = new Departement(id, "Biologie");
        when(departementRepository.findById(id)).thenReturn(Optional.of(dep));

        // Act
        Departement result = departementService.retrieveDepartement(id);

        // Assert
        assertNotNull(result);
        assertEquals("Biologie", result.getNomDepart());
        verify(departementRepository, times(1)).findById(id);
    }

    @Test
    void deleteDepartement() {
        // Arrange
        int id = 1;
        Departement dep = new Departement(id, "Philosophie");
        when(departementRepository.findById(id)).thenReturn(Optional.of(dep));
        doNothing().when(departementRepository).delete(dep);

        // Act
        departementService.deleteDepartement(id);

        // Assert
        verify(departementRepository, times(1)).delete(dep);
    }
}
