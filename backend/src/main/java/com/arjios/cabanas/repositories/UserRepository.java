package com.arjios.cabanas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.arjios.cabanas.entities.User;
import com.arjios.cabanas.projections.UserDetailsProjection;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String username);

	@Query(nativeQuery = true, value = """ 
										SELECT tb_user.email AS username, tb_user.password, 
											tb_user.role_id AS roleId, tb_role.authority 
										FROM tb_user 
										INNER JOIN tb_role 
										ON tb_role.id = tb_user.role_id
										WHERE tb_user.email = :email
			""")
	UserDetailsProjection findtUserAndRoleByEmail(String email);
	
}	

