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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class ClientService implements IClientService {


    private final IClientRepository clientRepository;

    private  final ObjectMapper objectMapper;

    public ClientService(IClientRepository clientRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    private final String MESSAGE ="No se encuentra el cliente ";

    @Override
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) throws NotClientAgeException {

        if (isUnderage(clientRequestDTO.getBirthDate())) {
            throw new NotClientAgeException("El cliente no puede ser menor de edad");
        }

         Client client = mapToEntity(clientRequestDTO);
         client.setCreationDate(LocalDate.now());
         clientRepository.save(client);
        return mapToDto(client);
        
    }

    @Override
    public ClientResponseDTO findById(Long id) {
         Client client = clientRepository.findById(id).orElseThrow(
                 () -> new NotFoundException(MESSAGE) );
        return mapToDto(client);
    }

    @Override
    public ClientResponseDTO update(ClientRequestUpdateDTO clientRequestUpdateDTO) {

        findById(clientRequestUpdateDTO.getId());
        Client client = mapToEntity(clientRequestUpdateDTO);
        client.setModificationDate(LocalDate.now());
        clientRepository.save(client);
        return mapToDto(client);
    }


    private boolean isUnderage(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() < 18;
    }


    private ClientResponseDTO mapToDto(Client client){
        return objectMapper.convertValue(client,ClientResponseDTO.class);
    }

    private  Client mapToEntity(ClientRequestDTO clientRequestDTO){
        return objectMapper.convertValue(clientRequestDTO,Client.class);
    }

    private  Client mapToEntity(ClientRequestUpdateDTO clientRequestUpdateDTO){
        return objectMapper.convertValue(clientRequestUpdateDTO,Client.class);
    }
}
