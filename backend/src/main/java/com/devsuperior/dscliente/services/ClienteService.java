package com.devsuperior.dscliente.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscliente.dto.ClienteDTO;
import com.devsuperior.dscliente.entities.Cliente;
import com.devsuperior.dscliente.exceptions.DatabaseException;
import com.devsuperior.dscliente.exceptions.ResourceNotFoundException;
import com.devsuperior.dscliente.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAllPage(PageRequest pageRequest) {
		Page<Cliente> list = repository.findAll(pageRequest);
		return list.map(cliente -> new ClienteDTO(cliente));
	}

	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		Cliente cliente = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClienteDTO(cliente);
	}

	@Transactional
	public ClienteDTO save(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		copyDtoToEntity(dto, cliente);
		cliente = repository.save(cliente);
		return new ClienteDTO(cliente);
	}
	
	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {
		try {
			Cliente cliente = repository.getOne(id);
			copyDtoToEntity(dto, cliente);
			cliente = repository.save(cliente);
			return new ClienteDTO(cliente);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(ClienteDTO dto, Cliente cliente) {
		cliente.setName(dto.getName());
		cliente.setCpf(dto.getCpf());
		cliente.setIncome(dto.getIncome());
		cliente.setBirthDate(dto.getBirthDate());
		cliente.setChildren(dto.getChildren());
	}

}
