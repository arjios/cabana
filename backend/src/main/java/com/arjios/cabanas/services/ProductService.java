package com.arjios.cabanas.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	private ProductRepository productRepository;
	
	private CategoryRepository categoryRepository;
	
	private LogRepository logRepository;
	
	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			LogRepository logRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.logRepository = logRepository;
	}

	@Transactional
	public List<ProductDTO> findAll() {
		List<Product> list = productRepository.findAll();
		return list.stream().map(c -> new ProductDTO(c, c.getCategory())).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> page = productRepository.findAll(pageable);
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
		try {
			Product prd = new Product();
			Log log = new Log();
			copyForEntity(dto, prd);
			prd.setDateInitial(Instant.now());
			dto.setDateInitial(prd.getDateInitial());
			updateLog("INSERT", dto, log);
			prd = productRepository.save(prd);
			log = logRepository.save(log);
			return new ProductDTO(prd);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Insert-Error: Recurso não encontrado: " + dto.getCategory());
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: " + dto.getCategory().getId());			
		}
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
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: " + dto.getCategory().getId());			
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
		entity.setNumber(dto.getNumber());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setShortDescription(dto.getShortDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		entity.setDate(Instant.now());
		entity.setActive(dto.getActive());
		entity.setDateInitial(dto.getDateInitial());
		entity.setDateFinal(dto.getDateFinal());
		Category cat = categoryRepository.getReferenceById(dto.getCategory().getId());
		entity.setCategory(cat);
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
