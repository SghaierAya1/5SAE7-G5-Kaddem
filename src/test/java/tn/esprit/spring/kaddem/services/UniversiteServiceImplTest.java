package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllUniversites() {
        // Arrange
        Universite uni1 = new Universite("Universite A");
        Universite uni2 = new Universite("Universite B");
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(uni1, uni2));

        // Act
        List<Universite> universites = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(2, universites.size());
        assertEquals("Universite A", universites.get(0).getNomUniv());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void addUniversite() {
        // Arrange
        Universite uni = new Universite("Universite C");
        when(universiteRepository.save(uni)).thenReturn(uni);

        // Act
        Universite result = universiteService.addUniversite(uni);

        // Assert
        assertNotNull(result);
        assertEquals("Universite C", result.getNomUniv());
        verify(universiteRepository, times(1)).save(uni);
    }

    @Test
    void updateUniversite() {
        // Arrange
        Universite uni = new Universite(1, "Universite D");
        when(universiteRepository.save(uni)).thenReturn(uni);

        // Act
        Universite result = universiteService.updateUniversite(uni);

        // Assert
        assertNotNull(result);
        assertEquals("Universite D", result.getNomUniv());
        verify(universiteRepository, times(1)).save(uni);
    }

    @Test
    void retrieveUniversite() {
        // Arrange
        int id = 1;
        Universite uni = new Universite(id, "Universite E");
        when(universiteRepository.findById(id)).thenReturn(Optional.of(uni));

        // Act
        Universite result = universiteService.retrieveUniversite(id);

        // Assert
        assertNotNull(result);
        assertEquals("Universite E", result.getNomUniv());
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    void deleteUniversite() {
        // Arrange
        int id = 1;
        Universite uni = new Universite(id, "Universite F");
        when(universiteRepository.findById(id)).thenReturn(Optional.of(uni));
        doNothing().when(universiteRepository).delete(uni);

        // Act
        universiteService.deleteUniversite(id);

        // Assert
        verify(universiteRepository, times(1)).delete(uni);
    }

    @Test
    void assignUniversiteToDepartement() {
        // Arrange
        int idUniversite = 1;
        int idDepartement = 2;
        Universite uni = new Universite(idUniversite, "Universite G");
        Departement dep = new Departement(idDepartement, "Departement X");
        uni.setDepartements(new HashSet<>());

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(uni));
        when(departementRepository.findById(idDepartement)).thenReturn(Optional.of(dep));
        when(universiteRepository.save(uni)).thenReturn(uni);

        // Act
        universiteService.assignUniversiteToDepartement(idUniversite, idDepartement);

        // Assert
        assertTrue(uni.getDepartements().contains(dep));
        verify(universiteRepository, times(1)).save(uni);
    }

    @Test
    void retrieveDepartementsByUniversite() {
        // Arrange
        int idUniversite = 1;
        Universite uni = new Universite(idUniversite, "Universite H");
        Set<Departement> departements = new HashSet<>(Arrays.asList(new Departement("Departement Y"), new Departement("Departement Z")));
        uni.setDepartements(departements);
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(uni));

        // Act
        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(idUniversite);

        // Assert
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findById(idUniversite);
    }
}
