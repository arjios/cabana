package com.arjios.cabanas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.arjios.cabanas.dto.ProductDTO;
import com.arjios.cabanas.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/pages")
	public ResponseEntity<Page<ProductDTO>> findAllPage(Pageable pageable){
		Page<ProductDTO> paged = productService.findAllPaged(pageable);
		return ResponseEntity.ok().body(paged);
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<ProductDTO> list = productService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR', 'ROLE_SELLER', 'ROLE_CLIENT', 'ROLE_STAFF')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO cat = productService.findById(id);
		return ResponseEntity.ok().body(cat);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {		
		dto = productService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {		
		productService.delete(id);
	}
}
