package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.CategoryDTO;
import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.CategoryRepository;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.services.exceptions.DatabaseException;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	private LogRepository logRepository;

	public CategoryService(CategoryRepository categoryRepository, LogRepository logRepository) {
		this.categoryRepository = categoryRepository;
		this.logRepository = logRepository;
	}

	@Transactional
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> page = categoryRepository.findAll(pageable);
		return page.map(p -> new CategoryDTO(p));
	}
	
	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Erro: Entidade não encontrada"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {		
		Log log = new Log();
		Category cat = new Category();
		cat.setName(dto.getName());
		cat = categoryRepository.save(cat);
		updateLog("INSERT", dto, log);
		log = logRepository.save(log);
		return new CategoryDTO(cat);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Log log = new Log();
			Category entity = categoryRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			updateLog("UPDATE", dto, log);
			log = logRepository.save(log);
			return new CategoryDTO(entity);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Error: Recurso não encontrado: " + id);
		}
	}
	
	public void delete(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Error: Recurso não encontrado: " + id);
		}
		try {
			Log log = new Log();
			Category cat = categoryRepository.findById(id).get();
			updateLog("DELETE" , cat.getName(), log);
			categoryRepository.deleteById(id);
			log = logRepository.save(log);
		} catch(DataIntegrityViolationException dive) {
			throw new DatabaseException("Error: Violação de Integridade: " + id);
		}
	}
	
	private void updateLog(String oper, CategoryDTO dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Category");
		log.setOperation(oper);
		log.setName(dto.getName());
	}
	
	private void updateLog(String oper, String name, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Category");
		log.setOperation(oper);
		log.setName(name);
	}

}
