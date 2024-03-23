package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.UserDTO;
import com.arjios.cabanas.dto.UserPasswordDTO;
import com.arjios.cabanas.entities.User;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.repositories.UserRepository;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserServices {
	
	private BCryptPasswordEncoder passwordEncoder;

	private UserRepository userRepository;
	
	private LogRepository logRepository;
	
	public UserServices(UserRepository userRepository, 
						LogRepository logRepository, 
						BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.logRepository = logRepository;
	}
	
	@Transactional
	public List<UserDTO> findAll() {
		List<User> list = userRepository.findAll();
		return list.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> page = userRepository.findAll(pageable);
		return page.map(u -> new UserDTO(u));
	}
	
	@Transactional
	public UserDTO findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Find-Error: Usuario não encontrado"));
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO insert(UserPasswordDTO dto) {
		User entity = new User();
		Log log = new Log();
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = userRepository.save(entity);
		updateLog("INSERT", dto, log);
		log = logRepository.save(log);
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		// Não altera email nem a senha!
		User entity = new User();
		Log log = new Log();
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		entity = userRepository.save(entity);
		updateLog("UPDATE", dto.getName(), log);
		log = logRepository.save(log);
		return new UserDTO(entity);
	}
	
	private void updateLog(String oper, UserDTO dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("User");
		log.setOperation(oper);
		log.setName(dto.getName());
	}
	
	private void updateLog(String oper, String name, Log log) {
		log.setUserCode(1L);
		log.setOrigin("User");
		log.setOperation(oper);
		log.setName(name);
	}
}
