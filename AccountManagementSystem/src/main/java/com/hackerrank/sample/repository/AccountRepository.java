package com.hackerrank.sample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.sample.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByCustomer(String customer);
	
	Optional<Account> findByIdAndStatusIsNot(String id, String status);

}
