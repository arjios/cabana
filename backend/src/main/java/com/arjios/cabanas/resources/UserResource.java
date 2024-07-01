package com.arjios.cabanas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR', 'ROLE_SELLER', 'ROLE_STAFF')")
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> list = userServices.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR', 'ROLE_SELLER', 'ROLE_STAFF')")
	@GetMapping(value = "/pages")
	public ResponseEntity<Page<UserDTO>> findAllPage(Pageable pageable) {
		Page<UserDTO> paged = userServices.findAllPaged(pageable);
		return ResponseEntity.ok().body(paged);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR', 'ROLE_SELLER', 'ROLE_STAFF')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO obj = userServices.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@PostMapping
	public ResponseEntity<UserPasswordDTO> insert(@Valid @RequestBody UserPasswordDTO dto) {
		UserDTO user = new UserDTO();
		user = userServices.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
		dto = userServices.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		userServices.delete(id);
	}
}
