package com.hd.finanMaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.service.impl.ClientService;
import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ClientController.class) // Solo carga el controlador y no el contexto completo
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClient() throws Exception {
        ClientRequestDTO requestDTO = new ClientRequestDTO();
        requestDTO.setBirthDate(LocalDate.of(2000, 1, 1)); // Asumiendo que el cliente es mayor de edad

        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(1L); // Asumiendo que el ID del cliente es 1

        when(clientService.createClient(any(ClientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Ajusta aquí
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        ClientRequestUpdateDTO requestDTO = new ClientRequestUpdateDTO();
        requestDTO.setId(1L);
        requestDTO.setBirthDate(LocalDate.of(2000, 1, 1)); // Asumiendo que el cliente es mayor de edad

        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(1L); // Asumiendo que el ID del cliente es 1

        when(clientService.update(any(ClientRequestUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        ClientResponseDTO responseDTO1 = new ClientResponseDTO();
        responseDTO1.setId(1L);
        ClientResponseDTO responseDTO2 = new ClientResponseDTO();
        responseDTO2.setId(2L);

        when(clientService.findAll()).thenReturn(List.of(responseDTO1, responseDTO2));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andDo(print());
    }
}


