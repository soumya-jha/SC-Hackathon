package com.hackerrank.sample.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;
	
	private final String ROLE_ADMIN = "ROLE_ADMIN";
	private final String ROLE_USER = "ROLE_USER";
	
	/**
	 * This method will create new user
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ ROLE_ADMIN })
	public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO account) throws Exception {
		logger.info("Inside createAccount in AccountController");
		AccountDTO accountResposne = accountService.createAccount(account);
		return new ResponseEntity<>(accountResposne, HttpStatus.OK);
	}

	/**
	 * This method will get account details of a user by Id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ ROLE_ADMIN, ROLE_USER })
	public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable String id) throws Exception {
		logger.info("Inside getAccountDetails in AccountController");
		AccountDTO accountResposne = accountService.getAccountDetails(id);
		return new ResponseEntity<>(accountResposne, HttpStatus.OK);
	}

	/**
	 * This method will update user account details
	 * 
	 * @param id
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ ROLE_ADMIN, ROLE_USER })
	public ResponseEntity<AccountDTO> updateAccount(@PathVariable String id, @RequestBody AccountDTO account)
			throws Exception {
		logger.info("Inside updateAccount in AccountController");
		AccountDTO accountResposne = accountService.updateAccount(id, account);
		return new ResponseEntity<>(accountResposne, HttpStatus.OK);
	}

	/**
	 * This method will withdraw amount from user account
	 * 
	 * @param id
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/{id}/withdrawal/{amount}")
	@ResponseStatus(HttpStatus.OK)
	@Secured({ ROLE_ADMIN, ROLE_USER })
	public BigDecimal withdrawAmount(@PathVariable String id, @PathVariable BigDecimal amount) throws Exception {
		logger.info("Inside withdrawAmount in AccountController");
		BigDecimal balanceAmount = accountService.withdrawAmount(id, amount);
		return balanceAmount;
	}

	/**
	 * This method will deposit amount to user account
	 * 
	 * @param id
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/{id}/deposit/{amount}")
	@ResponseStatus(HttpStatus.OK)
	@Secured({ ROLE_ADMIN, ROLE_USER })
	public BigDecimal depositAmount(@PathVariable String id, @PathVariable BigDecimal amount) throws Exception {
		logger.info("Inside depositAmount in AccountController");
		BigDecimal balanceAmount = accountService.depositAmount(id, amount);
		return balanceAmount;
	}

	/**
	 * This method will delete/inactivate user account by Id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Secured({ ROLE_ADMIN })
	public String deleteAccount(@PathVariable String id) throws Exception {
		logger.info("Inside deleteAccount in AccountController");
		String response = accountService.deleteAccount(id);
		return response;
	}

}
