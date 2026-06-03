package model;

/**
 * Account - Abstract base class for all bank accounts.
 *
 * OOP Concepts:
 *  - Abstraction  : Abstract class with abstract method calculateInterest()
 *  - Encapsulation: All fields are private, accessed via getters/setters
 */
public abstract class Account {

    // ── Private fields (Encapsulation) ───────────────────────
    private int    accountNumber;
    private String accountHolder;
    private String accountType;
    private double balance;
    private String pinHash;       // SHA-256 hash of the PIN
    private String createdAt;
    private boolean active;

    // ── Constructor ──────────────────────────────────────────
    public Account(int accountNumber, String accountHolder,
                   String accountType,  double balance,
                   String pinHash,      String createdAt) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.accountType   = accountType;
        this.balance       = balance;
        this.pinHash       = pinHash;
        this.createdAt     = createdAt;
        this.active        = true;
    }

    // ── Abstract method (Abstraction) ────────────────────────
    /**
     * Each account type calculates interest differently.
     * Must be implemented by subclasses.
     */
    public abstract double calculateInterest();

    // ── Business methods ─────────────────────────────────────
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > this.balance) return false;
        this.balance -= amount;
        return true;
    }

    // ── Getters & Setters (Encapsulation) ────────────────────
    public int    getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public String getAccountType()   { return accountType;   }
    public double getBalance()       { return balance;       }
    public String getPinHash()       { return pinHash;       }
    public String getCreatedAt()     { return createdAt;     }
    public boolean isActive()        { return active;        }

    public void setBalance(double balance)   { this.balance = balance; }
    public void setActive(boolean active)    { this.active  = active;  }
    public void setAccountHolder(String name){ this.accountHolder = name; }

    // ── toString ─────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "Account No : %d%n" +
            "Holder     : %s%n" +
            "Type       : %s%n" +
            "Balance    : %.2f%n" +
            "Created At : %s%n" +
            "Status     : %s",
            accountNumber, accountHolder, accountType,
            balance, createdAt, (active ? "Active" : "Inactive")
        );
    }
}
