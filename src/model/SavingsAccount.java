package model;

/**
 * SavingsAccount - Concrete subclass of Account.
 *
 * OOP Concepts:
 *  - Inheritance  : Extends Account
 *  - Polymorphism : Overrides calculateInterest()
 */
public class SavingsAccount extends Account {

    private static final double ANNUAL_INTEREST_RATE = 4.0; // 4% per annum
    private static final double MINIMUM_BALANCE      = 1000.0;

    // ── Constructor ──────────────────────────────────────────
    public SavingsAccount(int accountNumber, String accountHolder,
                          double balance,    String pinHash,
                          String createdAt) {
        super(accountNumber, accountHolder, "SAVINGS", balance, pinHash, createdAt);
    }

    // ── Overridden method (Polymorphism) ─────────────────────
    @Override
    public double calculateInterest() {
        return getBalance() * (ANNUAL_INTEREST_RATE / 100.0);
    }

    // ── Override withdraw: enforce minimum balance ────────────
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if ((getBalance() - amount) < MINIMUM_BALANCE) {
            System.out.println("[!] Withdrawal failed: Balance cannot fall below minimum " + MINIMUM_BALANCE);
            return false;
        }
        return super.withdraw(amount);
    }

    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    public static double getAnnualInterestRate() {
        return ANNUAL_INTEREST_RATE;
    }
}
