package com.arjios.cabanas.logs;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_logs")
public class Logs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userCode;
	private String origin;
	private String operation;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant writedAT;
	
	public Logs() {
	}

	public Logs(Long id, Long userCode, String origin, String operation, Instant writedAT) {
		this.id = id;
		this.userCode = userCode;
		this.origin = origin;
		this.operation = operation;
		this.writedAT = writedAT;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserCode() {
		return userCode;
	}
	
	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Instant getWritedAT() {
		return writedAT;
	}

	@PrePersist
	public void prePersistAt() {
		writedAT = Instant.now();
	}
	
	@PreUpdate
	public void preUpdateAt() {
		writedAT = Instant.now();
	}
	
	@PreRemove
	public void preRemove() {
		writedAT = Instant.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logs other = (Logs) obj;
		return Objects.equals(id, other.id);
	}

}
