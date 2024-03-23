package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.RoleDTO;
import com.arjios.cabanas.entities.Role;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.repositories.RoleRepository;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;
	
	private LogRepository logRepository;
	
	public RoleService(RoleRepository roleRepository, LogRepository logRepository) {
		this.roleRepository = roleRepository;
		this.logRepository = logRepository;
	}

	@Transactional
	public List<RoleDTO> findAll() {
		List<Role> list = roleRepository.findAll();
		return list.stream().map(r -> new RoleDTO(r)).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<RoleDTO> findAllPaged(Pageable pageable) {
		Page<Role> page = roleRepository.findAll(pageable);
		return page.map(p -> new RoleDTO(p));
	}
	
	@Transactional
	public RoleDTO findById(Long id) {
		Optional<Role> obj = roleRepository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Find-Error: Entidade não encontrada"));
		return new RoleDTO(entity);
	}
	
	@Transactional
	public RoleDTO insert(RoleDTO dto) {
		Role entity = new Role();
		Log log = new Log();
		entity.setAuthority(dto.getAuthority());
		Role role = roleRepository.save(entity);
		updateLog("INSERT", dto, log);
		log = logRepository.save(log);
		return new RoleDTO(role);
	}

	@Transactional
	public RoleDTO update(Long id, RoleDTO dto) {
		try {
			Log log = new Log();
			Role entity = roleRepository.getReferenceById(id);
			entity.setAuthority(dto.getAuthority());
			Role role = roleRepository.save(entity);
			updateLog("UPDATE", dto, log);
			log = logRepository.save(log);
			return new RoleDTO(role);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Insert-Error: Recurso não encontrado: " + id);
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: " + dto.getId());			
		}
	}

	@Transactional
	public void delete(Long id) {
		if(!roleRepository.existsById(id)) {
			throw new ResourceNotFoundException("Delete-Error: Recurso não encontrado: " + id);
		}
		try {
			Role role = roleRepository.findById(id).get();
			Log log = new Log();
			updateLog("DELETE", role.getAuthority(), log);
			roleRepository.deleteById(id);
			log = logRepository.save(log);
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: " + id);			
		}
	}
	
	private void updateLog(String oper, RoleDTO dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Role");
		log.setOperation(oper);
		log.setName(dto.getAuthority());
	}
	
	private void updateLog(String oper, String name, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Role");
		log.setOperation(oper);
		log.setName(name);
	}
}
