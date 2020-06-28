package com.wallet.security.dto;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

//todo: SpringSecurity+Jwt 8.6.3 - Classe/Campos usados na Autenticacao e obtencao do Tolken
@SuppressWarnings("deprecation")
@Data
public class JwtAuthenticationDTO {
	
	@NotNull(message = "Informe um email")
	@NotEmpty(message = "Informe um email")
	private String email;

	@NotNull(message = "Informe uma senha")
	@NotEmpty(message = "Informe uma senha")
	private String password;

}
