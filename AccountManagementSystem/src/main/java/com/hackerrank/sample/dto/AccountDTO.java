package com.hackerrank.sample.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private String id;

	@NotNull
	@JsonProperty("account_holder_name")
	private String account_holder_name;

	@NotNull
	@JsonProperty("account_holder_type")
	private String account_holder_type;

	@NotNull
	@JsonProperty("country")
	private String country;
	
	@NotNull
	@JsonProperty("currency")
	private String currency;

	@NotNull
	@JsonProperty("customer")
	private String customer;

	@JsonProperty("fingerprint")
	private String fingerprint;

	@NotNull
	@JsonProperty("last4")
	private String last4;

	@JsonProperty("metadata")
	private String metadata;

	@NotNull
	@JsonProperty("status")
	private String status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount_holder_name() {
		return account_holder_name;
	}

	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}


	public String getAccount_holder_type() {
		return account_holder_type;
	}

	public void setAccount_holder_type(String account_holder_type) {
		this.account_holder_type = account_holder_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}