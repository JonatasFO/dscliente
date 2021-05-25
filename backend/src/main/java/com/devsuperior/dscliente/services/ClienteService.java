package com.devsuperior.dscliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.dscliente.dto.ClienteDTO;
import com.devsuperior.dscliente.entities.Cliente;
import com.devsuperior.dscliente.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Page<ClienteDTO> findAllPage(PageRequest pageRequest) {
		Page<Cliente> list = repository.findAll(pageRequest);
		return list.map(cliente -> new ClienteDTO(cliente));
	}

}
