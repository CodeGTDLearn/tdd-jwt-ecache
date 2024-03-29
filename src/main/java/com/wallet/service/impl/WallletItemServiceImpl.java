package com.wallet.service.impl;

import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;
import com.wallet.util.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class WallletItemServiceImpl implements WalletItemService{

	@Autowired
	WalletItemRepository repository;
	
	@Value("${pagination.items_per_page}")
	private int itemsPerPage;


	@Override
	//todo: ecache7 - reseta cache p/ atualizar cache - (update desatualiza caches)
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public WalletItem save(WalletItem i) {
		return repository.save(i);
	}

	@Override
	public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {
		
		@SuppressWarnings("deprecation")
		PageRequest pg = new PageRequest(page, itemsPerPage);
		
		return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
	}

	//todo: ecache6 - cacheando metodo no service
	@Override
	@Cacheable(value = "findByWalletAndType")
	public List<WalletItem> findByWalletAndType(Long wallet, TypeEnum type) {
		return repository.findByWalletIdAndType(wallet, type);
	}

	@Override
	public BigDecimal sumByWalletId(Long wallet) {
		return repository.sumByWalletId(wallet);
	}

	@Override
	public Optional<WalletItem> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	//todo: ecache7 - reseta cache p/ atualizar cache - (delete desatualiza caches)
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
