package com.arjios.cabanas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arjios.cabanas.dto.ProductDTO;
import com.arjios.cabanas.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/pages")
	public ResponseEntity<Page<ProductDTO>> findAllPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "order", defaultValue = "name") String order,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), order);	
		Page<ProductDTO> paged = productService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(paged);
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<ProductDTO> list = productService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO cat = productService.findById(id);
		return ResponseEntity.ok().body(cat);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {		
		dto = productService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id) {		
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
