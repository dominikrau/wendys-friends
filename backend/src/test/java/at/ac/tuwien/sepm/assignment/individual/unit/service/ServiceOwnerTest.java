package at.ac.tuwien.sepm.assignment.individual.unit.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ServiceOwnerTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private HorseService horseService;

    @MockBean
    private OwnerDao ownerDao;

    @MockBean
    HorseDao horseDao;

    @Test
    @DisplayName("Finding owner by name should return the correct owner")
    public void getOwnerById_shouldReturnCorrectOwner() {
        Owner owner = new Owner("Hansi");
        Mockito.when(ownerDao.findOneById(1L)).thenReturn(owner);
        String expected = "Hansi";
        String got = ownerService.findOneById(1L).getName();
        assertEquals(expected, got);
    }

    @Test
    @DisplayName("Deleting owner with negative ID should throw ValidationException")
    public void deleteOwnerById_incorrectId_shouldThrowValidationException() {
        assertThrows(ValidationException.class,
            () -> ownerService.deleteOwnerById(-10L));
    }
}
