DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(250) NOT NULL,
  user_role VARCHAR(10)
);

INSERT INTO user (username, password, user_role) VALUES
  ('admin', 'admin', 'ADMIN'),
  ('user', 'user', 'USER');
  

DROP TABLE IF EXISTS account;
  
CREATE TABLE account (
id VARCHAR(50) NOT NULL PRIMARY KEY, 
account_holder_name VARCHAR(50) NOT NULL,
account_holder_type VARCHAR(50) NOT NULL,
country VARCHAR(2) NOT NULL,
currency VARCHAR(3) NOT NULL,
customer VARCHAR(16) NOT NULL,
fingerprint VARCHAR(50) NOT NULL,
last4 VARCHAR(4) NOT NULL,
metadata VARCHAR(300),
status VARCHAR(20) NOT NULL,
balance_id VARCHAR(50),
deposit_count INT NOT NULL,
created_date TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS account_balance;

CREATE table account_balance (
id VARCHAR(50) NOT NULL PRIMARY KEY,
balance DECIMAL NOT NULL,
last4 VARCHAR(4) NOT NULL
);

ALTER TABLE account
    ADD FOREIGN KEY (balance_id) 
    REFERENCES account_balance(id);
