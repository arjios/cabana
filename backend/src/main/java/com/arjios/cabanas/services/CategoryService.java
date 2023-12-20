package com.arjios.cabanas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.CategoryDTO;
import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.repositories.CategoryRepository;
import com.arjios.cabanas.services.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
	}
	
	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Erro: Entidade não encontrada"));
		return new CategoryDTO(entity);
	}
}
