package model;

/**
 * Transaction - Model class for a bank transaction.
 *
 * OOP Concept: Encapsulation (all fields private, accessed via getters)
 */
public class Transaction {

    private int    transactionId;
    private int    accountNumber;
    private String transactionType;  // DEPOSIT / WITHDRAWAL
    private double amount;
    private double balanceAfter;
    private String transactionDate;
    private String remarks;

    // ── Constructor ──────────────────────────────────────────
    public Transaction(int transactionId, int accountNumber,
                       String transactionType, double amount,
                       double balanceAfter,    String transactionDate,
                       String remarks) {
        this.transactionId   = transactionId;
        this.accountNumber   = accountNumber;
        this.transactionType = transactionType;
        this.amount          = amount;
        this.balanceAfter    = balanceAfter;
        this.transactionDate = transactionDate;
        this.remarks         = remarks;
    }

    // ── Getters ───────────────────────────────────────────────
    public int    getTransactionId()   { return transactionId;   }
    public int    getAccountNumber()   { return accountNumber;   }
    public String getTransactionType() { return transactionType; }
    public double getAmount()          { return amount;          }
    public double getBalanceAfter()    { return balanceAfter;    }
    public String getTransactionDate() { return transactionDate; }
    public String getRemarks()         { return remarks;         }

    @Override
    public String toString() {
        return String.format("[%s] %-12s  Amount: %10.2f  Balance After: %10.2f  Date: %s",
            transactionId, transactionType, amount, balanceAfter, transactionDate);
    }
}
