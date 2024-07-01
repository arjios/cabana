package com.arjios.cabanas.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.arjios.cabanas.dto.OrderDTO;
import com.arjios.cabanas.entities.Order;
import com.arjios.cabanas.entities.logs.Log;
import com.arjios.cabanas.repositories.LogRepository;
import com.arjios.cabanas.repositories.OrderRepository;
import com.arjios.cabanas.services.exceptions.DatabaseException;
import com.arjios.cabanas.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private OrderRepository orderRepository;
	
	private LogRepository logRepository;
	
	public OrderService(OrderRepository orderRepository, LogRepository logRepository) {
		this.orderRepository = orderRepository;
		this.logRepository = logRepository;
	}

	@Transactional
	public List<OrderDTO> findAll() {
		List<Order> list = orderRepository.findAll();
		return list.stream().map(c -> new OrderDTO(c)).collect(Collectors.toList());
	}
	
	@Transactional
	public Page<OrderDTO> findAllPaged(Pageable pageable) {
		Page<Order> page = orderRepository.findAll(pageable);
		return page.map(p -> new OrderDTO(p));
	}
	
	@Transactional
	public OrderDTO findById(Long id) {
		Optional<Order> obj = orderRepository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException("Find-Error: Entidade não encontrada"));
		return new OrderDTO(entity);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {		
		try {
			Order ord = new Order();
			Log log = new Log();
			copyForEntity(dto, ord);
			ord.setOpenTime(Instant.now());
			dto.setOpenTime(ord.getOpenTime());
			updateLog("INSERT", dto, log);
			ord = orderRepository.save(ord);
			log = logRepository.save(log);
			return new OrderDTO(ord);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Insert-Error: Recurso não encontrado: " + dto.getId());
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: " + dto.getId());			
		} catch(IllegalArgumentException iae) {
			throw new ResourceNotFoundException("Insert-Error: Argumento numero invalido: " + dto.getId());
		}
	}

	@Transactional
	public OrderDTO update(Long id, OrderDTO dto) {
		try {
			Log log = new Log();
			Order entity = orderRepository.getReferenceById(id);
			copyForEntity(dto, entity);
			updateLog("UPDATE", dto, log);
			entity = orderRepository.save(entity);
			log = logRepository.save(log);
			return new OrderDTO(entity);
		} catch(EntityNotFoundException enfe) {
			throw new ResourceNotFoundException("Update-Error: Recurso não encontrado: " + id);
		} catch(DataIntegrityViolationException dive) {
			throw new ResourceNotFoundException("Insert-Error: Violação de Integridade: ");			
		}
	}
	
	public void delete(Long id) {
		if(!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException("Delete-Error: Recurso não encontrado: " + id);
		}

		try {
			Log log = new Log();
			Order ord = orderRepository.findById(id).get();
			updateLog("DELETE", ord.getId(), log);
			orderRepository.deleteById(id);
			log = logRepository.save(log);
		} catch(DataIntegrityViolationException dive) {
			throw new DatabaseException("Delete-Error: Violação de Integridade: " + id);
		}
	}
	
	private void copyForEntity(OrderDTO dto, Order entity) {

		entity.setOpenTime(dto.getOpenTime());
		entity.setCloseTime(null);
		entity.setOrderStatus(dto.getOrderStatus());
		entity.setTotal(dto.getTotal());

	}
	
	private void updateLog(String oper, Long dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Order");
		log.setOperation(oper);
		log.setId(dto);
	}
	
	private void updateLog(String oper, OrderDTO dto, Log log) {
		log.setUserCode(1L);
		log.setOrigin("Order");
		log.setOperation(oper);
	}

}
