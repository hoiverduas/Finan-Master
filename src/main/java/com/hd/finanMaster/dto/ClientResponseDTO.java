package com.hd.finanMaster.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDTO extends ClientRequestUpdateDTO {

    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private LocalDate creationDate;
    private LocalDate modificationDate;
}
