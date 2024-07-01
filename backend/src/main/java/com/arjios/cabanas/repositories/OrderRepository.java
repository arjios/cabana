package com.arjios.cabanas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arjios.cabanas.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
