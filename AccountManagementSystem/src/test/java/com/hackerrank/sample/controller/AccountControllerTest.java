package com.hackerrank.sample.controller;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.service.AccountService;
import com.hackerrank.sample.util.TestUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;

	@Test
	public void testCreateAccount() throws Exception {

		AccountDTO accountDto = TestUtil.createAccountDTO();
		Mockito.when(accountService.createAccount(Mockito.any(AccountDTO.class))).thenReturn(accountDto);

		ResponseEntity<AccountDTO> response = accountController.createAccount(accountDto);

		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		Mockito.verify(accountService, Mockito.times(1)).createAccount(Mockito.any(AccountDTO.class));

	}

	@Test
	public void testGetAccountDetails() throws Exception {

		AccountDTO accountDto = TestUtil.createAccountDTO();
		Mockito.when(accountService.getAccountDetails(Mockito.anyString())).thenReturn(accountDto);

		ResponseEntity<AccountDTO> response = accountController.getAccountDetails("67f2f0ffb17f4690903f4400");

		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("67f2f0ffb17f4690903f4400", response.getBody().getId());

		Mockito.verify(accountService, Mockito.times(1)).getAccountDetails(Mockito.anyString());

	}

	@Test
	public void testUpdateAccount() throws Exception {

		AccountDTO accountDto = TestUtil.createAccountDTO();
		Mockito.when(accountService.updateAccount(Mockito.anyString(), Mockito.any(AccountDTO.class)))
				.thenReturn(accountDto);

		ResponseEntity<AccountDTO> response = accountController.updateAccount("67f2f0ffb17f4690903f4400", accountDto);

		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("INR", response.getBody().getCurrency());

		Mockito.verify(accountService, Mockito.times(1)).updateAccount(Mockito.anyString(),
				Mockito.any(AccountDTO.class));

	}

	@Test
	public void testWithdrawAmount() throws Exception {

		Mockito.when(accountService.withdrawAmount(Mockito.anyString(), Mockito.any(BigDecimal.class)))
				.thenReturn(new BigDecimal(800));

		BigDecimal balAmount = accountController.withdrawAmount("67f2f0ffb17f4690903f4400", new BigDecimal(200));

		Assert.assertEquals(new BigDecimal(800), balAmount);

		Mockito.verify(accountService, Mockito.times(1)).withdrawAmount(Mockito.anyString(),
				Mockito.any(BigDecimal.class));
	}

	@Test
	public void testDepositAmount() throws Exception {

		Mockito.when(accountService.depositAmount(Mockito.anyString(), Mockito.any(BigDecimal.class)))
				.thenReturn(new BigDecimal(1200));

		BigDecimal balAmount = accountController.depositAmount("67f2f0ffb17f4690903f4400", new BigDecimal(200));

		Assert.assertEquals(new BigDecimal(1200), balAmount);

		Mockito.verify(accountService, Mockito.times(1)).depositAmount(Mockito.anyString(),
				Mockito.any(BigDecimal.class));
	}

	@Test
	public void testDeleteAccount() throws Exception {

		Mockito.when(accountService.deleteAccount(Mockito.anyString())).thenReturn("Account deleted successfully");

		String response = accountController.deleteAccount("67f2f0ffb17f4690903f4400");

		Assert.assertEquals("Account deleted successfully", response);

		Mockito.verify(accountService, Mockito.times(1)).deleteAccount(Mockito.anyString());
	}

}
