package at.ac.tuwien.sepm.assignment.individual.unit.rest;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
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
public class RestOwnerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    @DisplayName("Get request for unknown route should throw NotFound")
    public void getRequest_unknownRoute_shouldThrowNotFound() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get request for all owners should return owner")
    public void getRequest_findAllOwners_shouldReturnOwner() throws Exception {
        List<Owner> owners = new ArrayList<>();
        owners.add(new Owner("Greta"));
        Mockito.when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Greta")));
    }

}
