package com.hd.finanMaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class ClientRequestUpdateDTO {

    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExceptionDTO {

        private String message;

    }
}
