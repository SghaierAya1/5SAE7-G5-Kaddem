package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllEtudiants() {
        // Arrange
        Etudiant et1 = new Etudiant("Alice", "Doe");
        Etudiant et2 = new Etudiant("Bob", "Smith");
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(et1, et2));

        // Act
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        // Assert
        assertEquals(2, etudiants.size());
        assertEquals("Alice", etudiants.get(0).getNomE());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void addEtudiant() {
        // Arrange
        Etudiant et = new Etudiant("John", "Doe");
        when(etudiantRepository.save(et)).thenReturn(et);

        // Act
        Etudiant result = etudiantService.addEtudiant(et);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomE());
        verify(etudiantRepository, times(1)).save(et);
    }

    @Test
    void updateEtudiant() {
        // Arrange
        Etudiant et = new Etudiant(1, "Jane", "Doe", null);
        when(etudiantRepository.save(et)).thenReturn(et);

        // Act
        Etudiant result = etudiantService.updateEtudiant(et);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getNomE());
        verify(etudiantRepository, times(1)).save(et);
    }

    @Test
    void retrieveEtudiant() {
        // Arrange
        int id = 1;
        Etudiant et = new Etudiant(id, "Michael", "Jordan", null);
        when(etudiantRepository.findById(id)).thenReturn(Optional.of(et));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(id);

        // Assert
        assertNotNull(result);
        assertEquals("Michael", result.getNomE());
        verify(etudiantRepository, times(1)).findById(id);
    }

    @Test
    void removeEtudiant() {
        // Arrange
        int id = 1;
        Etudiant et = new Etudiant(id, "Alice", "Wonderland", null);
        when(etudiantRepository.findById(id)).thenReturn(Optional.of(et));
        doNothing().when(etudiantRepository).delete(et);

        // Act
        etudiantService.removeEtudiant(id);

        // Assert
        verify(etudiantRepository, times(1)).delete(et);
    }

    @Test
    void assignEtudiantToDepartement() {
        // Arrange
        int etudiantId = 1;
        int departementId = 2;
        Etudiant etudiant = new Etudiant(etudiantId, "Alice", "Doe", null);
        Departement departement = new Departement(departementId, "Informatique");

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);

        // Assert
        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void addAndAssignEtudiantToEquipeAndContract() {
        // Arrange
        Etudiant etudiant = new Etudiant(1, "John", "Doe", null);
        int contratId = 2;
        int equipeId = 3;

        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();

        // Ensure the etudiants collection is initialized
        equipe.setEtudiants(new HashSet<>()); // or `new ArrayList<>()` if you’re using a List

        when(contratRepository.findById(contratId)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));

        // Act
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, contratId, equipeId);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
    }
    @Test
    void getEtudiantsByDepartement() {
        // Arrange
        int departementId = 1;
        Etudiant et1 = new Etudiant("Alice", "Doe");
        Etudiant et2 = new Etudiant("Bob", "Smith");
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(departementId)).thenReturn(Arrays.asList(et1, et2));

        // Act
        List<Etudiant> etudiants = etudiantService.getEtudiantsByDepartement(departementId);

        // Assert
        assertEquals(2, etudiants.size());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(departementId);
    }
}
