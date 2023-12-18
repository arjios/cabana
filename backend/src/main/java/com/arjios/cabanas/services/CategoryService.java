package com.arjios.cabanas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.CategoryDTO;
import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.repositories.CategoryRepository;

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
}
