package com.hd.finanMaster.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import com.hd.finanMaster.exception.NotClientAgeException;
import com.hd.finanMaster.exception.NotFoundException;
import com.hd.finanMaster.model.Client;
import com.hd.finanMaster.repository.IClientRepository;
import com.hd.finanMaster.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientService(IClientRepository clientRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    private final String MESSAGE = "Client not found.";

    /**
     * Creates a new client and saves it to the repository.
     * Throws NotClientAgeException if the client is a minor.
     */
    @Override
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) throws NotClientAgeException {
        if (isUnderage(clientRequestDTO.getBirthDate())) {
            throw new NotClientAgeException("The client cannot be a minor.");
        }

        Client client = mapToEntity(clientRequestDTO);
        client.setCreationDate(LocalDate.now());
        client.setModificationDate(null);
        clientRepository.save(client);
        return mapToDto(client);
    }

    /**
     * Finds a client by their ID.
     * Throws NotFoundException if the client is not found.
     */
    @Override
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MESSAGE));
        return mapToDto(client);
    }

    /**
     * Updates an existing client.
     * Sets the modification date to the current date.
     */
    @Override
    public ClientResponseDTO update(ClientRequestUpdateDTO clientRequestUpdateDTO) {
        findById(clientRequestUpdateDTO.getId());
        Client client = mapToEntity(clientRequestUpdateDTO);
        client.setModificationDate(LocalDate.now());
        clientRepository.save(client);
        return mapToDto(client);
    }

    /**
     * Deletes a client by their ID.
     * Throws NotFoundException if the client is not found.
     */
    @Override
    public String deleteById(Long id) {
        findById(id);
        clientRepository.deleteById(id);
        return "Client with ID: " + id + " has been deleted.";
    }

    /**
     * Retrieves all clients from the repository.
     */
    @Override
    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    /**
     * Checks if the client is underage based on their birth date.
     */
    private boolean isUnderage(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() < 18;
    }

    /**
     * Maps a Client entity to a ClientResponseDTO using ObjectMapper.
     */
    private ClientResponseDTO mapToDto(Client client) {
        return objectMapper.convertValue(client, ClientResponseDTO.class);
    }

    /**
     * Maps a ClientRequestDTO to a Client entity using ObjectMapper.
     */
    private Client mapToEntity(ClientRequestDTO clientRequestDTO) {
        return objectMapper.convertValue(clientRequestDTO, Client.class);
    }

    /**
     * Maps a ClientRequestUpdateDTO to a Client entity using ObjectMapper.
     */
    private Client mapToEntity(ClientRequestUpdateDTO clientRequestUpdateDTO) {
        return objectMapper.convertValue(clientRequestUpdateDTO, Client.class);
    }
}
