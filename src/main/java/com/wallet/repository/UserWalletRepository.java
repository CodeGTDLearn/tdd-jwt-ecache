package com.wallet.repository;

import com.wallet.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

    //todo: SpringSecurity+Jwt 9 - Restringir acesso aos dados
    // (usuario comente acessa items da sua carateira)

    //todo: SpringSecurity+Jwt 9.1 - Busca carteira conforme UserID+WalletID
    Optional<UserWallet> findByUsersIdAndWalletId(Long userId ,Long walletId);
}

