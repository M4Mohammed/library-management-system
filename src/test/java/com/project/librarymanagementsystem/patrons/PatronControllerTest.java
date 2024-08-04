package com.project.librarymanagementsystem.patrons;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RequiredArgsConstructor
public class PatronControllerTest {

    private final MockMvc mvc;

    @MockBean
    private final PatronService patronService;

    private final ObjectMapper objectMapper;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = Patron.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .address(Address.builder()
                        .street("Test Street")
                        .city("Test City")
                        .state("Test State")
                        .zipCode("12345")
                        .build())
                .mobile("1234567890")
                .email("example@abc.com")
                .build();
    }

    @Test
    void getAllPatrons() throws Exception {
        Mockito.when(patronService.getAllPatrons()).thenReturn(Collections.singletonList(patron));

        mvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(patron.getFirstName()));
    }

    @Test
    void getPatronById() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(patronService.getPatronById(id)).thenReturn(patron);

        mvc.perform(get("/api/patrons/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(patron.getFirstName()));
    }

    @Test
    @WithMockUser
    void addPatron() throws Exception {
        Mockito.when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        mvc.perform(post("/api/patrons")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(patron.getFirstName()));
    }

    @Test
    @WithMockUser
    void updatePatron() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(patronService.updatePatron(id, patron)).thenReturn(patron);

        mvc.perform(put("/api/patrons/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(patron.getFirstName()));
    }


    @Test
    @WithMockUser
    void deleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/patrons/" + id))
                .andExpect(status().isNoContent());
    }

}
