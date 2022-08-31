package at.ac.tuwien.sepm.assignment.individual.unit.rest;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class RestHorseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorseService horseService;

    @Test
    @DisplayName("Get request for not existing owner with ID should respond notFound")
    public void getRequest_findOwnerById_shouldThrowNotFound() throws Exception {
        Mockito.when(horseService.findOneById(1L)).thenThrow(new NotFoundException("Not found"));
        mockMvc.perform(get("/horses/1"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get request for all owners should return owner")
    public void getRequest_findAllHorses_shouldReturnHorse() throws Exception {
        LocalDate date = LocalDate.of(Integer.parseInt("2010"), Integer.parseInt("10"), Integer.parseInt("10"));
        LocalDateTime dateTime = LocalDateTime.now();

        Owner owner = new Owner(1L, "Money Boy", dateTime, dateTime);
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse(1L, dateTime, dateTime, "Hansi", 3, date, null, 1L, owner, "MORGAN", null));

        Mockito.when(horseService.findAll()).thenReturn(horses);
        mockMvc.perform(get("/horses"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hansi")));
    }

}
