package com.wallet.security.service;

import com.wallet.entity.User;
import com.wallet.security.JwtUserFactory;
import com.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	//todo: SpringSecurity+Jwt 3 - Implementa interface UsernameNotFoundException
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		//todo: SpringSecurity+Jwt 3.1 - Checa se User(aqui usa email, mas pd qqer coisa) esta na DB-de-Autenticacao
		Optional<User> user = userService.findByEmail(email);

		if (user.isPresent()) {

			//todo: SpringSecurity+Jwt 3.2 - Cria JwtUser, usando a JwtUserFactory
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}