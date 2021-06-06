package com.hackerrank.sample.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.model.Account;
import com.hackerrank.sample.repository.AccountRepository;
import com.hackerrank.sample.util.PdfGenerationUtil;

@Service
public class ReportsService {

	@Autowired
	private AccountRepository accountRepository;

	@Value("${account.create.minBalance}")
	private int minBalance;

	public void generateReport() {

		List<Account> accountList = accountRepository.findAll();
		if (null != accountList && !accountList.isEmpty()) {
			AtomicInteger minBalCount = new AtomicInteger();
			AtomicInteger noOfNewAccounts = new AtomicInteger();
			AtomicInteger noOfAccountWithMaxDeposit = new AtomicInteger();
			AtomicInteger maxDepositCount = new AtomicInteger();
			accountList.stream().forEach(account -> {
				Period period = Period.between(account.getCreated_date(), LocalDate.now());
				if (period.getDays() <= 30) {
					if (account.getAccountBalance().getBalance().compareTo(new BigDecimal(minBalance)) == 0) {
						minBalCount.getAndAdd(1);
					}

					noOfNewAccounts.getAndAdd(1); // Calculating number of new accounts

					if (account.getDeposit_count() > maxDepositCount.intValue()) {
						maxDepositCount.set(account.getDeposit_count());
						noOfAccountWithMaxDeposit.set(1);
					} else if (account.getDeposit_count() == maxDepositCount.intValue()) {
						noOfAccountWithMaxDeposit.getAndAdd(1);
					}
				}
			});
			PdfGenerationUtil.generatePdf(minBalCount, noOfNewAccounts, noOfAccountWithMaxDeposit);
		} else {
			AtomicInteger zero = new AtomicInteger();
			PdfGenerationUtil.generatePdf(zero, zero, zero);
		}
	}
}
