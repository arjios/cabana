package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.UserDTO;
import com.arjios.cabanas.dto.UserPasswordDTO;
import com.arjios.cabanas.entities.Role;
import com.arjios.cabanas.entities.User;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.repositories.RoleRepository;
import com.arjios.cabanas.repositories.UserRepository;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired	
	private LogRepository logRepository;

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
		Role role = roleRepository.getReferenceById(dto.getRole().getId());
		if(role == null) {
			throw new UsernameNotFoundException("Usuario sem permissão");
		}
		Log log = new Log();
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		// Senha tem que chegar codificada do front!
		entity.setPassword(dto.getPassword());
		entity.setRole(dto.getRole());
		entity = userRepository.save(entity);
		updateLog("INSERT", dto, log);
		log = logRepository.save(log);
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		// Não altera email nem a senha!
		try {
			Log log = new Log();
//			User entity = new User();
//			UserDTO usrDTO = findById(id);
//			entity.setName(dto.getName());
//			entity.setLastName(dto.getLastName());
			Integer status = userRepository.updateUserAndRoleById(
											dto.getName(),
											dto.getLastName(),
											dto.getRole().getId(),
											id);
			System.out.println(status);
//			log.setStatus(status);
			updateLog("UPDATE", dto, log);
			log = logRepository.save(log);
			return dto;
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Update-Error: Recurso não encontrado: " + dto.getRole());
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Update-Error: Violação de Integridade: " + dto.getRole().getId());			
		}
	}
	
	@Transactional
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Delete-Error: Usuario não existe: " + id);
		}
		try {
			User user = userRepository.findById(id).get();
			Log log = new Log();
			updateLog("DELETE", user.getEmail(), log);
			userRepository.deleteById(id);
			log = logRepository.save(log);
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Delete-Error: (User)Violação de Integridade: " + id);			
		}
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// podemos usar tambem o SQL native findtUserAndRoleByEmail(String email)
		// User user = new User()
		// UserDetailsProjection udp = userRepository.findUserAndRoleByEmail(String email)
		// if(udp == null) {
		// 		throw new UsernameNotFoundException("Error: Usuario não existe."); 
		// }
		// user.setName = udp.getUsername()
		// user.setPassword = udp.getPassword()
		// user.setRoleId() = udp.getRoleId()
		// user.setAuthority = udp.getAuthority()
		
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Error: Usuario não existe.");
		}
		return user;
	}

}
