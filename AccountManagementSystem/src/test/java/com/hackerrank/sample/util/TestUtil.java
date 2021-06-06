package com.hackerrank.sample.util;

import java.math.BigDecimal;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.model.Account;
import com.hackerrank.sample.model.AccountBalance;

public class TestUtil {
	
	public static AccountDTO createAccountDTO() {
		AccountDTO accountDto = new AccountDTO();
		accountDto.setId("67f2f0ffb17f4690903f4400");
		accountDto.setAccount_holder_name("TestName");
		accountDto.setAccount_holder_type("individual");
		accountDto.setCountry("IN");
		accountDto.setCurrency("INR");
		accountDto.setCustomer("cust1");
		accountDto.setLast4("6789");
		accountDto.setStatus("new");
		return accountDto;
	}
	
	public static Account createAccountEntity() {
		Account account = new Account();
		account.setId("67f2f0ffb17f4690903f4400");
		account.setAccount_holder_name("TestName");
		account.setAccount_holder_type("individual");
		account.setCountry("IN");
		account.setCurrency("INR");
		account.setCustomer("cust1");
		account.setLast4("6789");
		account.setStatus("new");
		account.setDeposit_count(1);
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setId("89f2f0ffb17f4690903f4400");
		accountBalance.setBalance(new BigDecimal(1000));
		accountBalance.setLast4("6789");
		account.setAccountBalance(accountBalance);
		return account;
	}
	
}
