package de.fhdo.city_mgmt_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {

	@NotBlank(message = "Name is Required")
	@Size(min = 5, max = 20, message = "Name should be min 5 characters and max 20 characters")
	private String name;
	@Email
	@NotBlank(message = "Email is Required")
	@Size(max = 35, message = "Email should not be more than 35 characters")
	private String email;
	private String phone;
	@NotBlank(message = "Password is Required")
	@Size(max = 15)
	private String password;
	@NotBlank(message = "Role is Required")
	private String role;

}
