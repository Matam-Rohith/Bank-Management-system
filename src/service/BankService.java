package service;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import model.Account;
import model.SavingsAccount;
import model.Transaction;

import java.util.List;

/**
 * BankService - Business logic layer.
 *
 * Sits between the UI (BankApp) and the DAO layer.
 * OOP Concept: Encapsulation of business rules
 */
public class BankService {

    // OOP: Polymorphism - reference is AccountDAO (interface)
    private final AccountDAO accountDAO;

    public BankService() {
        // AccountDAOImpl is the concrete class (Dependency)
        this.accountDAO = new AccountDAOImpl();
    }

    // ── Create Account ────────────────────────────────────────
    public int createAccount(String name, double initialDeposit, String pin) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[!] Name cannot be empty.");
            return -1;
        }
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            System.out.println("[!] PIN must be exactly 4 digits.");
            return -1;
        }
        if (initialDeposit < SavingsAccount.getMinimumBalance()) {
            System.out.printf("[!] Initial deposit must be at least %.2f%n",
                              SavingsAccount.getMinimumBalance());
            return -1;
        }
        return accountDAO.createAccount(name.trim(), "SAVINGS", initialDeposit, pin);
    }

    // ── Deposit ───────────────────────────────────────────────
    public boolean deposit(int accNo, String pin, double amount) {
        if (!accountDAO.validatePin(accNo, pin)) {
            System.out.println("[!] Invalid account number or PIN.");
            return false;
        }
        boolean result = accountDAO.deposit(accNo, amount, null);
        if (result) {
            System.out.printf("[✓] Deposit successful! New balance: %.2f%n",
                              accountDAO.getBalance(accNo));
        }
        return result;
    }

    // ── Withdraw ──────────────────────────────────────────────
    public boolean withdraw(int accNo, String pin, double amount) {
        if (!accountDAO.validatePin(accNo, pin)) {
            System.out.println("[!] Invalid account number or PIN.");
            return false;
        }
        boolean result = accountDAO.withdraw(accNo, amount, null);
        if (result) {
            System.out.printf("[✓] Withdrawal successful! New balance: %.2f%n",
                              accountDAO.getBalance(accNo));
        }
        return result;
    }

    // ── Check Balance ─────────────────────────────────────────
    public void checkBalance(int accNo, String pin) {
        if (!accountDAO.validatePin(accNo, pin)) {
            System.out.println("[!] Invalid account number or PIN.");
            return;
        }
        double balance = accountDAO.getBalance(accNo);
        System.out.printf("%n  Account  : %d%n  Balance  : INR %.2f%n", accNo, balance);
    }

    // ── Display Account Details ───────────────────────────────
    public void displayAccount(int accNo, String pin) {
        if (!accountDAO.validatePin(accNo, pin)) {
            System.out.println("[!] Invalid account number or PIN.");
            return;
        }
        Account acc = accountDAO.findByAccountNumber(accNo);
        if (acc == null) {
            System.out.println("[!] Account not found.");
            return;
        }
        System.out.println("\n" + "─".repeat(45));
        System.out.println(acc.toString());
        if (acc instanceof SavingsAccount sa) {
            System.out.printf("Interest/yr: %.2f%n", sa.calculateInterest());
        }
        System.out.println("─".repeat(45));
    }

    // ── Transaction History ───────────────────────────────────
    public void showTransactionHistory(int accNo, String pin) {
        if (!accountDAO.validatePin(accNo, pin)) {
            System.out.println("[!] Invalid account number or PIN.");
            return;
        }
        List<Transaction> txns = accountDAO.getTransactionHistory(accNo);
        System.out.println("\n" + "─".repeat(75));
        System.out.printf("  Transaction History for Account: %d (Last 10)%n", accNo);
        System.out.println("─".repeat(75));
        if (txns.isEmpty()) {
            System.out.println("  No transactions found.");
        } else {
            txns.forEach(t -> System.out.println("  " + t));
        }
        System.out.println("─".repeat(75));
    }

    // ── List All Accounts (Admin) ─────────────────────────────
    public void listAllAccounts() {
        List<Account> accounts = accountDAO.getAllAccounts();
        System.out.println("\n" + "═".repeat(55));
        System.out.println("  ALL ACCOUNTS");
        System.out.println("═".repeat(55));
        if (accounts.isEmpty()) {
            System.out.println("  No accounts found.");
        } else {
            accounts.forEach(a ->
                System.out.printf("  [%d] %-25s  Balance: %.2f%n",
                    a.getAccountNumber(), a.getAccountHolder(), a.getBalance())
            );
        }
        System.out.println("═".repeat(55));
    }
}
