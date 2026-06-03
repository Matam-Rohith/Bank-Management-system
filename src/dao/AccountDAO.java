package dao;

import model.Account;
import model.Transaction;
import java.util.List;

/**
 * AccountDAO - Data Access Object interface.
 *
 * OOP Concept:
 *  - Abstraction  : Interface defines WHAT to do, not HOW
 *  - Polymorphism : AccountDAOImpl provides the HOW
 */
public interface AccountDAO {

    /**
     * Create a new bank account in the database.
     * @return generated account number, or -1 on failure
     */
    int createAccount(String holderName, String accountType,
                      double initialDeposit, String pin);

    /**
     * Find an account by account number.
     * @return Account object, or null if not found
     */
    Account findByAccountNumber(int accountNumber);

    /**
     * Deposit money into an account.
     * @return true if successful
     */
    boolean deposit(int accountNumber, double amount, String remarks);

    /**
     * Withdraw money from an account.
     * @return true if successful
     */
    boolean withdraw(int accountNumber, double amount, String remarks);

    /**
     * Get the current balance.
     * @return balance, or -1 if account not found
     */
    double getBalance(int accountNumber);

    /**
     * Fetch transaction history for an account.
     * @return List of Transaction objects
     */
    List<Transaction> getTransactionHistory(int accountNumber);

    /**
     * Validate the PIN for an account.
     * @return true if PIN is correct
     */
    boolean validatePin(int accountNumber, String pin);

    /**
     * List all active accounts.
     */
    List<Account> getAllAccounts();
}
