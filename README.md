## Environment:
- Java version: 1.8
- Maven version: 3.*
- Spring Boot version: 2.2.1.RELEASE

## Data:
Example of account JSON object:
```
{
  "id": "ba_1IvW8m2eZvKYlo2CiFaCD0eg",
  "object": "bank_account",
  "account_holder_name": "user1",
  "account_holder_type": "individual",
  "country": "US",
  "currency": "usd",
  "customer": "custId123",
  "fingerprint": "j1CvuuIQNXSIdZuK",
  "last4": "6789",
  "metadata": {},
  "status": "new"
}

```

Example of account balance JSON object:
```
{
  "id": "ba_1IvW8m2eZvKYlo2CiFaCD0ef",
  "last4": "6789",
  "balance": 10000
}

```

##Attributes of account
####id - string
Unique identifier for the object.

####account_holder_name - string
The name of the person or business that owns the bank account.

####account_holder_type - string
The type of entity that holds the account. This can be either individual or company.

####country - string
Two-letter ISO code representing the country the bank account is located in.

####currency - currency
Three-letter ISO code for the currency paid out to the bank account.

####customer - string
The ID of the customer that the bank account is associated with.

####fingerprint - string
Uniquely identifies this particular bank account. Generated on creation.

####last4 - string
The last four digits of the bank account number.

####metadata - hash
Set of key-value pairs that you can attach to an object. This can be useful for storing additional information about the object in a structured format.

####status - string
Possible values are new, validated, verified, verification_failed, errored or inactive.

##Attributes of account balance
####id - string
Unique identifier for the object.

####balance - BigDecimal
Balance amount.

####last4 - string
The last four digits of the bank account number.

## Requirements:
The task is to implement account management service in the following way:

####API's

**POST** request to `/api/auth`:

- implement role based username password authentication
- return JWT token on valid authentication


**POST** request to `/api/account/create`:

- creates a new account
- expects a JSON account object without an id property as a body payload. 
- validate if the account is already existing based on input parameters


**POST** request to `/api/account/update/{id}`:

- updates existing account
- expects a JSON account object with an id property as a body payload. 
- Only editable fields are metadata, country, currency, fingerprint, status


**POST** request to `/api/account/{id}/deposit`:

- Deposit amount to account
- expects a JSON account balance object with an id property  . 
- validates the account number and deposit limit 
- returns balance as response.


**POST** request to `/api/account/{id}/withdrawal`:

- Withdraw amount from account
- validates the account number and withdrawal limit
- returns balance as response

**DELETE** request to `/api/account/delete/{id}`:

- Deletes existing account by marking status as inactive.

**GET** request to `/account/{id}`:

- return a account details
- the response code is 200, and the response body is account object.

#### Instructions
- Users should be authenticated before any operations. (Role based authentication, for ex: Only admin users can create/delete the account)
- Validation should be in place for fields before any operation for ex: User can update only few editable fields, all other fields are non-editable
#####Withdrawal / Deposit: 
- user can only withdraw 0%<w%<100%0%<w%<100% (of the current balance) or deposit 0%<d%<100%0%<d%<100% (of the current balance) at a time, this is to be decided by the bank.
- For ex: for user A, If balance=1000, w=10%, d=20%
- w and d can be modified based on 3 months account activities, for example, if user A has made more than 5 deposits and no withdrawals from past 3 months, then w can be 20%.

#####Reporting: 
- Bank would need a monthly report of number of new accounts created, number of accounts with min balance and number of accounts with maximum deposits.
#####Rate limiting API's - Do not use third party solution
-   A rate limiter is a tool that monitors the number of requests per a window time a service agrees to allow. If the request count exceeds the number agreed by the service owner and the user (in a decided window time), the rate limiter blocks all the excess calls (say by throwing exceptions). The user can be a human or any other service (ex: in a micro service-based architecture)
-   Expectation is to create your own solution.
- 	Different APIs would have different rate limits
-	Should be possible to set default limits. This will be applied when an API specific limit has not been configured.
-	The solution should consider rate limiting based on User+API combination
-	The solution should be plug and play (easily configurable)

#####Deliverable
-	Completely working solution with Java source code
-	Production ready code (Integration and unit tests are mandatory)
-	Fully functional API’s.
-	All API’s with rate limiting.

## Commands
- run: 
```bash
mvn clean package; java -jar target/accountmgmt-1.0.jar
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```
