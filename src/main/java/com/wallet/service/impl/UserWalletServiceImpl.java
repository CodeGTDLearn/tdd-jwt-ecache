package com.wallet.service.impl;

import com.wallet.entity.UserWallet;
import com.wallet.repository.UserWalletRepository;
import com.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWalletServiceImpl implements UserWalletService{

	@Autowired
	UserWalletRepository repository;
	
	@Override
	public UserWallet save(UserWallet uw) {
		return repository.save(uw);
	}

	//todo: SpringSecurity+Jwt 9.2 - Busca carteira por(UserID+WalletID), no service
	@Override
	public Optional<UserWallet> findByUsersIdAndWalletId(Long userId, Long walletId) {
		return repository.findByUsersIdAndWalletId(userId, walletId);
	}

}
