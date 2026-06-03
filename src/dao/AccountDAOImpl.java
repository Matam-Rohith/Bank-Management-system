package dao;

import db.DBConnection;
import model.Account;
import model.SavingsAccount;
import model.Transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AccountDAOImpl - JDBC implementation of AccountDAO.
 *
 * OOP Concepts:
 *  - Polymorphism : Implements AccountDAO interface
 *  - Encapsulation: All DB logic hidden inside this class
 */
public class AccountDAOImpl implements AccountDAO {

    // ── Helper: Hash PIN using SHA-256 ────────────────────────
    private String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(pin.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // ── Helper: Log transaction ───────────────────────────────
    private void logTransaction(Connection conn, int accNo,
                                String type, double amount,
                                double balanceAfter, String remarks)
            throws SQLException {
        String sql = "INSERT INTO transactions (account_number, transaction_type, " +
                     "amount, balance_after, remarks) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setDouble(4, balanceAfter);
            ps.setString(5, remarks);
            ps.executeUpdate();
        }
    }

    // ── createAccount ─────────────────────────────────────────
    @Override
    public int createAccount(String holderName, String accountType,
                             double initialDeposit, String pin) {
        String sql = "INSERT INTO accounts (account_holder, account_type, balance, pin_hash) " +
                     "VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return -1;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, holderName);
            ps.setString(2, accountType.toUpperCase());
            ps.setDouble(3, initialDeposit);
            ps.setString(4, hashPin(pin));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newAccNo = rs.getInt(1);
                // Log the initial deposit as a transaction
                logTransaction(conn, newAccNo, "DEPOSIT", initialDeposit, initialDeposit, "Account Opening");
                return newAccNo;
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] createAccount: " + e.getMessage());
        }
        return -1;
    }

    // ── findByAccountNumber ───────────────────────────────────
    @Override
    public Account findByAccountNumber(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND is_active = TRUE";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SavingsAccount(
                    rs.getInt("account_number"),
                    rs.getString("account_holder"),
                    rs.getDouble("balance"),
                    rs.getString("pin_hash"),
                    rs.getString("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] findByAccountNumber: " + e.getMessage());
        }
        return null;
    }

    // ── validatePin ───────────────────────────────────────────
    @Override
    public boolean validatePin(int accountNumber, String pin) {
        String sql = "SELECT pin_hash FROM accounts WHERE account_number = ? AND is_active = TRUE";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("pin_hash");
                return storedHash.equals(hashPin(pin));
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] validatePin: " + e.getMessage());
        }
        return false;
    }

    // ── deposit ───────────────────────────────────────────────
    @Override
    public boolean deposit(int accountNumber, double amount, String remarks) {
        if (amount <= 0) { System.out.println("[!] Amount must be positive."); return false; }

        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try {
            conn.setAutoCommit(false);  // Begin transaction

            // Update balance
            String updateSql = "UPDATE accounts SET balance = balance + ? " +
                               "WHERE account_number = ? AND is_active = TRUE";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setDouble(1, amount);
                ps.setInt(2, accountNumber);
                int rows = ps.executeUpdate();
                if (rows == 0) { conn.rollback(); return false; }
            }

            double newBalance = getBalance(accountNumber);
            logTransaction(conn, accountNumber, "DEPOSIT", amount, newBalance,
                           remarks != null ? remarks : "Deposit");

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("[DAO ERROR] deposit: " + e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
        return false;
    }

    // ── withdraw ──────────────────────────────────────────────
    @Override
    public boolean withdraw(int accountNumber, double amount, String remarks) {
        if (amount <= 0) { System.out.println("[!] Amount must be positive."); return false; }

        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try {
            conn.setAutoCommit(false);

            // Check current balance and enforce minimum
            double currentBalance = getBalance(accountNumber);
            double minimumBalance = SavingsAccount.getMinimumBalance();
            if ((currentBalance - amount) < minimumBalance) {
                System.out.printf("[!] Insufficient funds. Min balance required: %.2f%n", minimumBalance);
                conn.rollback();
                return false;
            }

            String updateSql = "UPDATE accounts SET balance = balance - ? " +
                               "WHERE account_number = ? AND is_active = TRUE";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setDouble(1, amount);
                ps.setInt(2, accountNumber);
                int rows = ps.executeUpdate();
                if (rows == 0) { conn.rollback(); return false; }
            }

            double newBalance = getBalance(accountNumber);
            logTransaction(conn, accountNumber, "WITHDRAWAL", amount, newBalance,
                           remarks != null ? remarks : "Withdrawal");

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("[DAO ERROR] withdraw: " + e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
        return false;
    }

    // ── getBalance ────────────────────────────────────────────
    @Override
    public double getBalance(int accountNumber) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ? AND is_active = TRUE";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return -1;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] getBalance: " + e.getMessage());
        }
        return -1;
    }

    // ── getTransactionHistory ─────────────────────────────────
    @Override
    public List<Transaction> getTransactionHistory(int accountNumber) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? " +
                     "ORDER BY transaction_date DESC LIMIT 10";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getInt("account_number"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getDouble("balance_after"),
                    rs.getString("transaction_date"),
                    rs.getString("remarks")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] getTransactionHistory: " + e.getMessage());
        }
        return list;
    }

    // ── getAllAccounts ────────────────────────────────────────
    @Override
    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE is_active = TRUE ORDER BY account_number";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs  = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SavingsAccount(
                    rs.getInt("account_number"),
                    rs.getString("account_holder"),
                    rs.getDouble("balance"),
                    rs.getString("pin_hash"),
                    rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] getAllAccounts: " + e.getMessage());
        }
        return list;
    }
}
