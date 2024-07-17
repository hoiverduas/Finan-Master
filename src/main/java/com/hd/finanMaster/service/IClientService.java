package com.hd.finanMaster.service;

import com.hd.finanMaster.dto.ClientRequestDTO;
import com.hd.finanMaster.dto.ClientRequestUpdateDTO;
import com.hd.finanMaster.dto.ClientResponseDTO;
import com.hd.finanMaster.exception.NotClientAgeException;

public interface IClientService {

    ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) throws NotClientAgeException;
    ClientResponseDTO findById(Long id);
    ClientRequestUpdateDTO update(ClientRequestUpdateDTO clientRequestUpdateDTO);



}
