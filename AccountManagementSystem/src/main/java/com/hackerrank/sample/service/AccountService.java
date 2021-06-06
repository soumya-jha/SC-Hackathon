package com.hackerrank.sample.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.exception.AccountManagementException;
import com.hackerrank.sample.exception.GenericException;
import com.hackerrank.sample.exception.ValidationException;
import com.hackerrank.sample.model.Account;
import com.hackerrank.sample.model.AccountBalance;
import com.hackerrank.sample.repository.AccountRepository;
import com.hackerrank.sample.util.UniqueIdGenerator;
import com.hackerrank.sample.validate.AccountValidator;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Value("${account.withdraw.limit.percentage}")
	private int w;

	@Value("${account.deposit.limit.percentage}")
	private int d;

	@Value("${account.create.minBalance}")
	private int minBalance;
	
	private final String STATUS_INACTIVE = "inactive";

	public AccountDTO createAccount(AccountDTO accountDto) throws AccountManagementException, ValidationException, Exception {
		try {
			AccountValidator.validateCreateAccountRequest(accountDto);
			Account existingAccount = accountRepository.findByCustomer(accountDto.getCustomer());
			if (null == existingAccount) {

				String fingerPrint = UniqueIdGenerator.fromBase10(System.currentTimeMillis());
				Account account = new Account();
				BeanUtils.copyProperties(accountDto, account);
				account.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 24));
				account.setFingerprint(fingerPrint);
				account.setDeposit_count(1);
				account.setCreated_date(LocalDate.now());
				AccountBalance balance = new AccountBalance();
				balance.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 24));
				balance.setBalance(new BigDecimal(minBalance));
				balance.setLast4(account.getLast4());
				balance.setAccount(account);
				account.setAccountBalance(balance);
				Account newAccount = accountRepository.save(account);
				AccountDTO responseDTO = new AccountDTO();
				BeanUtils.copyProperties(newAccount, responseDTO);

				return responseDTO;
			} else {
				throw new AccountManagementException("Account Details Already Exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		}catch (ValidationException ex) {
			throw new ValidationException(ex.getMessage());
		}catch (Exception ex) {
			throw new GenericException("Exception occurred while creating account");
		}
	}

	public AccountDTO updateAccount(String id, AccountDTO accountDto) throws AccountManagementException {

		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				Account existingAccount = optional.get();
				if (STATUS_INACTIVE.equals(existingAccount.getStatus())) {
					throw new AccountManagementException("Account is Inactive");
				}
				if (null != accountDto.getCountry() && !accountDto.getCountry().isEmpty())
					existingAccount.setCountry(accountDto.getCountry());
				if (null != accountDto.getCurrency() && !accountDto.getCurrency().isEmpty())
					existingAccount.setCurrency(accountDto.getCurrency());
				if (null != accountDto.getFingerprint() && !accountDto.getFingerprint().isEmpty())
					existingAccount.setFingerprint(accountDto.getFingerprint());
				if (null != accountDto.getStatus() && !accountDto.getStatus().isEmpty())
					existingAccount.setStatus(accountDto.getStatus());
				Account updatedAccount = accountRepository.save(existingAccount);
				AccountDTO responseDTO = new AccountDTO();
				BeanUtils.copyProperties(updatedAccount, responseDTO);
				return responseDTO;
			} else {
				throw new AccountManagementException("Account Does Not Exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		} catch (Exception ex) {
			throw new GenericException("Exception occurred while updating account details");
		}
	}

	public BigDecimal withdrawAmount(String id, BigDecimal amount) throws AccountManagementException {
		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				Account existingAccount = optional.get();
				if (STATUS_INACTIVE.equals(existingAccount.getStatus())) {
					throw new AccountManagementException("Account is Inactive");
				}
				AccountBalance accountBalance = existingAccount.getAccountBalance();
				BigDecimal currBalance = accountBalance.getBalance();
				if (amount.compareTo(
						(currBalance.multiply(BigDecimal.valueOf(w)).divide(BigDecimal.valueOf(100)))) <= 0) {
					accountBalance.setBalance(currBalance.subtract(amount));
					existingAccount.setAccountBalance(accountBalance);
					Account updatedAccount = accountRepository.save(existingAccount);
					return updatedAccount.getAccountBalance().getBalance();

				} else {
					throw new AccountManagementException("Cannot withdraw more than " + w + " % of current balance");
				}
			} else {
				throw new AccountManagementException("Account Does Not Exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		} catch (Exception ex) {
			throw new GenericException("Exception occurred while amount withdrawal");
		}

	}

	public BigDecimal depositAmount(String id, BigDecimal amount) throws AccountManagementException {
		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				Account existingAccount = optional.get();
				if (STATUS_INACTIVE.equals(existingAccount.getStatus())) {
					throw new AccountManagementException("Account is Inactive");
				}
				AccountBalance accountBalance = existingAccount.getAccountBalance();
				BigDecimal currBalance = accountBalance.getBalance();
				if (amount.compareTo(
						(currBalance.multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(100)))) <= 0) {
					accountBalance.setBalance(currBalance.add(amount));
					existingAccount.setAccountBalance(accountBalance);
					existingAccount.setDeposit_count(existingAccount.getDeposit_count() + 1);
					Account updatedAccount = accountRepository.save(existingAccount);
					return updatedAccount.getAccountBalance().getBalance();
				} else {
					throw new AccountManagementException("Cannot deposit more than " + d + " % of current balance");
				}
			} else {
				throw new AccountManagementException("Account Does Not Exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		} catch (Exception ex) {
			throw new GenericException("Exception occurred while depositing amount");
		}
	}

	public AccountDTO getAccountDetails(String id) throws AccountManagementException {
		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				Account existingAccount = optional.get();
				if (STATUS_INACTIVE.equals(existingAccount.getStatus())) {
					throw new AccountManagementException("Account is Inactive");
				}
				AccountDTO accountDTO = new AccountDTO();
				BeanUtils.copyProperties(existingAccount, accountDTO);
				return accountDTO;
			} else {
				throw new AccountManagementException("Account Does Not Exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		} catch (Exception ex) {
			throw new GenericException("Exception occurred while retrieving account details");
		}
	}

	public String deleteAccount(String id) throws AccountManagementException {
		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				Account existingAccount = optional.get();
				if (STATUS_INACTIVE.equals(existingAccount.getStatus())) {
					throw new AccountManagementException("Account is already inactive");
				}
				existingAccount.setStatus("inactive");
				accountRepository.save(existingAccount);
				return "Account deleted successfully";
			} else {
				throw new AccountManagementException("Account Does Not exists");
			}
		} catch (AccountManagementException ex) {
			throw new AccountManagementException(ex.getMessage());
		} catch (Exception ex) {
			throw new GenericException("Exception occurred while deleting account");
		}

	}

}
