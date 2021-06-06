package com.hackerrank.sample.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalanceDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("balance")
	private BigDecimal balance;

	@JsonProperty("last4")
	private String last4;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

}