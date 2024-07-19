package com.hd.finanMaster.controller;

import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import com.hd.finanMaster.exception.NotClientAgeException;
import com.hd.finanMaster.service.impl.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Create a new client and return the created client details with status 201 Created
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) throws NotClientAgeException {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequestDTO));
    }

    // Update an existing client and return the updated client details with status 200 OK
    @PutMapping
    public ResponseEntity<ClientResponseDTO> update(@RequestBody ClientRequestUpdateDTO clientRequestUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(clientRequestUpdateDTO));
    }

    // Delete a client by ID and return status 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String message = clientService.deleteById(id);
        return ResponseEntity.ok(message);
    }


    // Retrieve all clients and return the list with status 200 OK
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll());
    }
}
