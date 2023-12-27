package com.arjios.cabanas.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.ProductDTO;
import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.entities.Product;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.CategoryRepository;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.repositories.ProductRepository;
import com.arjios.cabanas.services.exceptions.DatabaseException;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private LogRepository logRepository;
	
	@Transactional
	public List<ProductDTO> findAll() {
		List<Product> list = productRepository.findAll();
		return list.stream().map(c -> new ProductDTO(c, c.getCategory())).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> page = productRepository.findAll(pageRequest);
		return page.map(p -> new ProductDTO(p, p.getCategory()));
	}
	
	@Transactional
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Find-Error: Entidade não encontrada"));
		return new ProductDTO(entity, entity.getCategory());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {		
		Product prd = new Product();
		Log log = new Log();
		copyForEntity(dto, prd);
		updateLog("INSERT", dto, log);
		prd = productRepository.save(prd);
		log = logRepository.save(log);
		return new ProductDTO(prd);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Log log = new Log();
			Product entity = productRepository.getReferenceById(id);
			copyForEntity(dto, entity);
			updateLog("UPDATE", dto, log);
			entity = productRepository.save(entity);
			log = logRepository.save(log);
			return new ProductDTO(entity);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Update-Error: Recurso não encontrado: " + id);
		}
	}
	
	public void delete(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Delete-Error: Recurso não encontrado: " + id);
		}

		try {
			Log log = new Log();
			Product prod = productRepository.findById(id).get();
			updateLog("DELETE", prod.getName(), log);
			productRepository.deleteById(id);
			log = logRepository.save(log);
		} catch(DataIntegrityViolationException dive) {
			throw new DatabaseException("Delete-Error: Violação de Integridade: " + id);
		}
	}
	
	private void copyForEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setShortDescription(dto.getShortDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		entity.setDate(Instant.now());
		Category cat = categoryRepository.getReferenceById(dto.getCategory().getId());
		entity.getCategory().setId(cat.getId());
	}
	
	private void updateLog(String oper, ProductDTO dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Product");
		log.setOperation(oper);
		log.setName(dto.getName());
	}
	
	private void updateLog(String oper, String name, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Product");
		log.setOperation(oper);
		log.setName(name);
	}

}
