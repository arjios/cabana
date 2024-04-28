package com.arjios.cabanas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arjios.cabanas.dto.UserDTO;
import com.arjios.cabanas.dto.UserPasswordDTO;
import com.arjios.cabanas.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	private UserServices userServices;
	
	public UserResource(UserServices userServices) {
		this.userServices = userServices;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> list = userServices.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/pages")
	public ResponseEntity<Page<UserDTO>> findAllPage(Pageable pageable) {
		Page<UserDTO> paged = userServices.findAllPaged(pageable);
		return ResponseEntity.ok().body(paged);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO obj = userServices.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<UserPasswordDTO> insert(@Valid @RequestBody UserPasswordDTO dto) {
		UserDTO user = new UserDTO();
		user = userServices.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
		dto = userServices.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
