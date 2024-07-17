package com.hd.finanMaster.controller;

import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import com.hd.finanMaster.exception.NotClientAgeException;
import com.hd.finanMaster.service.impl.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private  final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) throws NotClientAgeException {
           return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequestDTO));
    }

    @PutMapping()
    public ResponseEntity<ClientResponseDTO> update(@RequestBody ClientRequestUpdateDTO clientRequestUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(clientRequestUpdateDTO));
    }
}
