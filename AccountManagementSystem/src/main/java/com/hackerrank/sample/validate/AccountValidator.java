package com.hackerrank.sample.validate;

import org.springframework.stereotype.Component;

import com.hackerrank.sample.dto.AccountDTO;
import com.hackerrank.sample.exception.ValidationException;
import com.hackerrank.sample.util.AccountManagementConstant;

@Component
public class AccountValidator {
	
	public static void validateCreateAccountRequest(AccountDTO accountDto) {
		if(null==accountDto.getAccount_holder_type() || "".equals(accountDto.getAccount_holder_type()) 
				|| (!AccountManagementConstant.ACCOUNT_HOLDER_TYPE_INDIVIDUAL.equals(accountDto.getAccount_holder_type()) 
						&& !AccountManagementConstant.ACCOUNT_HOLDER_TYPE_COMPANY.equals(accountDto.getAccount_holder_type()))){
			throw new ValidationException("Account Holder Type can be individual or company");
		}
		
		if(null==accountDto.getStatus() || "".equals(accountDto.getStatus()) 
				|| (!AccountManagementConstant.ACCOUNT_STATUS_NEW.equals(accountDto.getStatus()) && !AccountManagementConstant.ACCOUNT_STATUS_ERRORED.equals(accountDto.getStatus())
						&& !AccountManagementConstant.ACCOUNT_STATUS_VALIDATED.equals(accountDto.getStatus()) && !AccountManagementConstant.ACCOUNT_STATUS_VERIFIED.equals(accountDto.getStatus())
						&& !AccountManagementConstant.ACCOUNT_STATUS_INACTIVE.equals(accountDto.getStatus()) && !AccountManagementConstant.ACCOUNT_STATUS_VERIFICATION_FAILED.equals(accountDto.getStatus()))){
			throw new ValidationException("Invalid account status");
		}
		
	}
	
	

}
