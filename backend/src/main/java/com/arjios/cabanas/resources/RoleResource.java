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

import com.arjios.cabanas.dto.RoleDTO;
import com.arjios.cabanas.services.RoleService;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {
	
	private RoleService roleService;
	
	public RoleResource(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@GetMapping(value = "/pages")
	public ResponseEntity<Page<RoleDTO>> findAllPage(Pageable pageable){
		Page<RoleDTO> paged = roleService.findAllPaged(pageable);
		return ResponseEntity.ok().body(paged);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@GetMapping
	public ResponseEntity<List<RoleDTO>> findAll() {
		List<RoleDTO> list = roleService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR', 'ROLE_SELLER', 'ROLE_STAFF')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
		RoleDTO rol = roleService.findById(id);
		return ResponseEntity.ok().body(rol);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<RoleDTO> insert(@RequestBody RoleDTO dto) {
		dto = roleService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody RoleDTO dto) {		
		dto = roleService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {		
		roleService.delete(id);
	}
}
