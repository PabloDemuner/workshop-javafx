package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	/**
	 * excess�o personalizada que carrega uma cole��o de erros possiveis com o (Map<> HashMap<>)
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>();
	
	public ValidationException (String msg) {
		super(msg);
		
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void addError (String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}
