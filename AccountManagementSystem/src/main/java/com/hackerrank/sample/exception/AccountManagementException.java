package com.hackerrank.sample.exception;

public class AccountManagementException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountManagementException(String msg) {
		super(msg);
	}
}
