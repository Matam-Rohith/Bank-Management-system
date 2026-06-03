-- ============================================================
-- Bank Management System - SQL Schema
-- Compatible with: MySQL 8.x / Oracle Database
-- Author: Matam Rohith
-- ============================================================

-- Create database (MySQL)
CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

-- ============================================================
-- TABLE: accounts
-- ============================================================
CREATE TABLE IF NOT EXISTS accounts (
    account_number    INT PRIMARY KEY AUTO_INCREMENT,  -- Oracle: use SEQUENCE + TRIGGER
    account_holder    VARCHAR(100)  NOT NULL,
    account_type      VARCHAR(20)   NOT NULL DEFAULT 'SAVINGS',
    balance           DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    pin_hash          VARCHAR(64)   NOT NULL,            -- Store SHA-256 hash of PIN
    created_at        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    is_active         BOOLEAN       DEFAULT TRUE
);

-- ============================================================
-- TABLE: transactions
-- ============================================================
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id    INT PRIMARY KEY AUTO_INCREMENT,
    account_number    INT           NOT NULL,
    transaction_type  VARCHAR(20)   NOT NULL,  -- DEPOSIT / WITHDRAWAL / TRANSFER
    amount            DECIMAL(15,2) NOT NULL,
    balance_after     DECIMAL(15,2) NOT NULL,
    transaction_date  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    remarks           VARCHAR(255),
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- ============================================================
-- Oracle Compatibility: Sequence & Trigger for AUTO_INCREMENT
-- Uncomment below if using Oracle Database instead of MySQL
-- ============================================================
/*
CREATE SEQUENCE account_seq START WITH 1001 INCREMENT BY 1;
CREATE SEQUENCE transaction_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER accounts_bir
BEFORE INSERT ON accounts
FOR EACH ROW
BEGIN
    SELECT account_seq.NEXTVAL INTO :new.account_number FROM dual;
END;
/

CREATE OR REPLACE TRIGGER transactions_bir
BEFORE INSERT ON transactions
FOR EACH ROW
BEGIN
    SELECT transaction_seq.NEXTVAL INTO :new.transaction_id FROM dual;
END;
/
*/

-- Sample data for testing
INSERT INTO accounts (account_holder, account_type, balance, pin_hash)
VALUES
  ('Matam Rohith', 'SAVINGS', 10000.00, SHA2('1234', 256)),
  ('Test User',    'SAVINGS',  5000.00, SHA2('5678', 256));
