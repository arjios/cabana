package com.arjios.cabanas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arjios.cabanas.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
