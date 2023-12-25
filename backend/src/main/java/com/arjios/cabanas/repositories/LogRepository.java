package com.arjios.cabanas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arjios.cabanas.entities.logs.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
