package com.hackerrank.sample.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.exception.AccountManagementException;
import com.hackerrank.sample.exception.ValidationException;
import com.hackerrank.sample.model.Account;
import com.hackerrank.sample.repository.AccountRepository;
import com.hackerrank.sample.util.TestUtil;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository repository;

	@Test
	public void testCreateAccount() throws AccountManagementException, ValidationException, Exception {

		AccountDTO accountDto = TestUtil.createAccountDTO();
		Account account = TestUtil.createAccountEntity();
		Mockito.when(repository.findByCustomer(Mockito.anyString())).thenReturn(null);
		Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

		AccountDTO response = accountService.createAccount(accountDto);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getId());
		Assert.assertEquals("new", response.getStatus());

		Mockito.verify(repository, Mockito.times(1)).findByCustomer(Mockito.anyString());
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Account.class));

	}

	@Test
	public void testUpdateAccount() {

		AccountDTO accountDto = TestUtil.createAccountDTO();
		Account account = TestUtil.createAccountEntity();
		accountDto.setStatus("verified");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(account));
		Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

		AccountDTO response = accountService.updateAccount("67f2f0ffb17f4690903f4400", accountDto);

		Assert.assertNotNull(response);
		Assert.assertEquals("verified", response.getStatus());

		Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Account.class));

	}

	@Test
	public void testWithdrawAmount() {

		ReflectionTestUtils.setField(accountService, "w", 10);

		Account account = TestUtil.createAccountEntity();
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(account));
		Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

		BigDecimal balance = accountService.withdrawAmount("67f2f0ffb17f4690903f4400", new BigDecimal(10));

		Assert.assertEquals(new BigDecimal(990), balance);

		Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Account.class));

	}

	@Test
	public void testDepositAmount() {

		ReflectionTestUtils.setField(accountService, "d", 20);

		Account account = TestUtil.createAccountEntity();
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(account));
		Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

		BigDecimal balance = accountService.depositAmount("67f2f0ffb17f4690903f4400", new BigDecimal(10));

		Assert.assertEquals(new BigDecimal(1010), balance);

		Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Account.class));

	}

	@Test
	public void testGetAccountDetails() {

		Account account = TestUtil.createAccountEntity();
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(account));

		AccountDTO response = accountService.getAccountDetails("67f2f0ffb17f4690903f4400");

		Assert.assertEquals("67f2f0ffb17f4690903f4400", response.getId());
		Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());

	}

	@Test
	public void testDeleteAccount() {

		Account account = TestUtil.createAccountEntity();
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(account));
		Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(null);

		String response = accountService.deleteAccount("67f2f0ffb17f4690903f4400");

		Assert.assertEquals("Account deleted successfully", response);

		Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Account.class));
	}

}
