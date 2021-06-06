 ##Implemented Features

 #### 1 - Authentication

 All APIs have role based authentication using JWT token
 Mandatory - JWT Token should begin with a "Bearer " String

 Two users are created in H2-database at server startup

 USER1 - user
 password - user
 Role - USER
 
 USER2 - admin
 password - admin
 Role- ADMIN
 
 Only users with role ADMIN can create/delete any account and view monthly report
 
 #### 2 - Rate Limiter
 
 Currently configured with 5 request per minute.
 It is configurable and can be updated in application.properties
 
 Note - There is a glitch identified and needs to be fixed. After one minute, the rate limiter takes 10 request per minute instead of 5.
 
 #### 3 - Report Generation
 
 A monthly report PDF is generated as per the requirement.
 Currently the PDF is created and saved in C:/temp (the program will create the folder, if not present) once the report API is invoked
 
 #### 4 - APIs

**POST** request to `/api/auth`:

- role based authentication returns JWT token on valid authentication from database


**POST** request to `/api/account/create`:

- creates a new account
- expects a JSON account object without an id property as a body payload. 
- validates if the account is already existing based on customerID
- validation applied on few fields, pending for others


**PUT** request to `/api/account/update/{id}`:

- updates an existing account
- expects a path variable "id"
- only country, currency, fingerprint, status will be updated, rest if provided in input will not get updated in Database
- input field validations are pending


**PUT** request to `/api/account/{id}/deposit/{amount}`:

- deposits amount to account 
- expects a path variable "id" and "amount"
- validates the account number and deposit limit 
- deposit limit is configured in application.properties
- returns balance as response.


**PUT** request to `/api/account/{id}/withdrawal/{amount}`:

- withdraws amount from account
- expects a path variable "id" and "amount"
- validates the account number and withdrawal limit
- withdrawal limit is configured in application.properties
- returns balance as response

**PUT** request to `/api/account/delete/{id}`:

- deletes existing account by marking status as inactive.

**GET** request to `/account/{id}`:

- returns account details in json format

**GET** request to `/api/report/generate`

- generates monthly report PDF and saves in C:/temp/Report.pdf
