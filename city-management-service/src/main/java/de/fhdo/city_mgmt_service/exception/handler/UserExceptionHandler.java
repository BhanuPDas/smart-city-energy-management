package de.fhdo.city_mgmt_service.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.fhdo.city_mgmt_service.exception.UserException;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public String handleUserException(UserException e, Model model) {
		model.addAttribute("msg", e.getMessage());
		return "app_error";
	}
}
