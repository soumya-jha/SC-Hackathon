package com.hackerrank.sample.model;

public class Error {

	public Error(int status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}

	private int status;
	private String errorMessage;

	public int getStatus() {
		return status;
	}

	public void setStatus(int i) {
		this.status = i;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
