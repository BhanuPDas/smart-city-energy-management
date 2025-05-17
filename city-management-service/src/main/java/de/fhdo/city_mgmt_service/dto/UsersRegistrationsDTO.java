package de.fhdo.city_mgmt_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersRegistrationsDTO {

	@NotBlank(message = "Name is Required")
	@Size(max=20)
	private String name;
	@Email
	@NotBlank(message = "Email is Required")
	@Size(max=35)
	private String email;
	private String phone;
	@NotBlank(message = "Password is Required")
	@Size(max=15)
	private String password;
	@NotBlank(message = "Role is Required")
	private String role;

}
