package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.CategoryDTO;
import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.logs.Logs;
import com.arjios.cabanas.repositories.CategoryRepository;
import com.arjios.cabanas.repositories.LogsRepository;
import com.arjios.cabanas.services.exceptions.DatabaseException;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private LogsRepository logRepository;
	
	@Transactional
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
	}
	
	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Erro: Entidade não encontrada"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {		
		Logs log = new Logs();
		log.setUserCode(1L);
		log.setOrigin("Category");
		log.setOperation("INSERT");
		log = logRepository.save(log);
		Category cat = new Category();
		cat.setName(dto.getName());
		cat = categoryRepository.save(cat);
		return new CategoryDTO(cat);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Logs log = new Logs();
			log.setUserCode(1L);
			log.setOrigin("Category");
			log.setOperation("UPDATE");
			log = logRepository.save(log);
			Category entity = categoryRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
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
			Logs log = new Logs();
			log.setUserCode(1L);
			log.setOrigin("Category");
			log.setOperation("DELETE");
			log = logRepository.save(log);
			categoryRepository.deleteById(id);
		} catch(DataIntegrityViolationException dive) {
			throw new DatabaseException("Error: Violação de Integridade: " + id);
		}
	}

}
