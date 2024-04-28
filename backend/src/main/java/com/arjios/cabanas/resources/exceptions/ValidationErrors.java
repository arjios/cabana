package com.arjios.cabanas.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<ErrosFieldMessages> errosFieldMessages = new ArrayList<>();

	public List<ErrosFieldMessages> getErrosFieldMessages() {
		return errosFieldMessages;
	}
	
	public void addError(String fieldName, String message) {
		errosFieldMessages.add(new ErrosFieldMessages(fieldName, message));
	}

}
