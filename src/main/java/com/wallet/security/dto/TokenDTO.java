package com.wallet.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//todo: SpringSecurity+Jwt 8.6.3 - TolkenDTO class
@Data
@AllArgsConstructor
public class TokenDTO {

	private String token;


}
