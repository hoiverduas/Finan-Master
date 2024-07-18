package com.hd.finanMaster.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.exception.NotClientAgeException;
import com.hd.finanMaster.model.Client;
import com.hd.finanMaster.repository.IClientRepository;
import com.hd.finanMaster.service.impl.ClientService;
import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClient() throws NotClientAgeException {
        // Datos de prueba
        ClientRequestDTO requestDTO = new ClientRequestDTO();
        requestDTO.setBirthDate(LocalDate.of(2000, 1, 1)); // Asumiendo que el cliente es mayor de edad

        Client client = new Client();
        client.setCreationDate(LocalDate.now()); // Configura la fecha de creación si es necesario

        ClientResponseDTO responseDTO = new ClientResponseDTO();

        // Configurar los mocks
        when(objectMapper.convertValue(requestDTO, Client.class)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(objectMapper.convertValue(client, ClientResponseDTO.class)).thenReturn(responseDTO);

        // Ejecutar el método
        ClientResponseDTO result = clientService.createClient(requestDTO);

        // Verificar el resultado
        assertNotNull(result);
        verify(clientRepository).save(client); // Verifica que el método save fue llamado con el objeto client
    }


    @Test
    void findById() {
        Long id = 1L;
        Client client = new Client();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(objectMapper.convertValue(client, ClientResponseDTO.class)).thenReturn(new ClientResponseDTO());

        ClientResponseDTO responseDTO = clientService.findById(id);

        assertNotNull(responseDTO);
        verify(clientRepository).findById(id);
    }

    @Test
    void update() {
        Long id = 1L;
        ClientRequestUpdateDTO requestUpdateDTO = new ClientRequestUpdateDTO();
        requestUpdateDTO.setId(id);
        Client client = new Client();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(objectMapper.convertValue(requestUpdateDTO, Client.class)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(objectMapper.convertValue(client, ClientResponseDTO.class)).thenReturn(new ClientResponseDTO());

        ClientResponseDTO responseDTO = clientService.update(requestUpdateDTO);

        assertNotNull(responseDTO);
        verify(clientRepository).save(client);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        Client client = new Client();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        clientService.deleteById(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    void findAll() {
        Client client = new Client();
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(objectMapper.convertValue(client, ClientResponseDTO.class)).thenReturn(new ClientResponseDTO());

        List<ClientResponseDTO> responseDTOs = clientService.findAll();

        assertFalse(responseDTOs.isEmpty());
        verify(clientRepository).findAll();
    }

}