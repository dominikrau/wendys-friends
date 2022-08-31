package at.ac.tuwien.sepm.assignment.individual.unit.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
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
public class ServiceHorseTest {

    @Autowired
    private HorseService horseService;

    @MockBean
    HorseDao horseDao;

    @Test
    @DisplayName("Creating horse without needed parameters should throw ValidationException")
    public void createHorse_withoutExpectedValues_shouldThrowValidationException() {
        Horse horse = new Horse(null, null, null, null, Integer.MIN_VALUE, null, null, null, null, null, null);
        assertThrows(ValidationException.class,
            () -> horseService.create(horse));
    }

    @Test
    @DisplayName("Finding all horses without horses in database should return null")
    public void findAllHorses_WithoutHorsesInDataBase_shouldReturnNull() {
        Mockito.when(horseDao.findAll()).thenReturn(null);
        assertNull(horseService.findAll());
    }

}
