package com.arjios.cabanas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arjios.cabanas.logs.Logs;

public interface LogsRepository extends JpaRepository<Logs, Long> {

}
