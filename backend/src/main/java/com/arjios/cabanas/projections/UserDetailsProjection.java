package com.arjios.cabanas.projections;

public interface UserDetailsProjection {
	
	String getUserName();
	String getPassword();
	Long getRoleId();
	String getAuthority();
}
