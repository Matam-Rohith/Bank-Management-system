package main;

import service.BankService;
import db.DBConnection;

import java.util.Scanner;

/**
 * BankApp - Main entry point and console menu UI.
 *
 * Author : Matam Rohith
 * College : SR University
 * Tech    : Java | MySQL | JDBC | OOP
 */
public class BankApp {

    private static final BankService bankService = new BankService();
    private static final Scanner     scanner     = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1  -> createAccount();
                case 2  -> deposit();
                case 3  -> withdraw();
                case 4  -> checkBalance();
                case 5  -> displayAccount();
                case 6  -> transactionHistory();
                case 7  -> bankService.listAllAccounts();
                case 0  -> running = false;
                default -> System.out.println("[!] Invalid choice. Try again.");
            }
        }

        DBConnection.closeConnection();
        System.out.println("\nThank you for using the Bank Management System. Goodbye!");
    }

    // ── Menu Options ──────────────────────────────────────────
    private static void printBanner() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("      🏦  BANK MANAGEMENT SYSTEM");
        System.out.println("         Java | MySQL | JDBC | OOP");
        System.out.println("         Author: Matam Rohith");
        System.out.println("═".repeat(50));
    }

    private static void printMenu() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("  MAIN MENU");
        System.out.println("─".repeat(40));
        System.out.println("  1. Create Account");
        System.out.println("  2. Deposit");
        System.out.println("  3. Withdraw");
        System.out.println("  4. Check Balance");
        System.out.println("  5. Display Account Details");
        System.out.println("  6. Transaction History");
        System.out.println("  7. List All Accounts");
        System.out.println("  0. Exit");
        System.out.println("─".repeat(40));
    }

    // ── Handlers ──────────────────────────────────────────────
    private static void createAccount() {
        System.out.println("\n[ Create New Account ]");
        String name = readString("Enter full name    : ");
        double deposit = readDouble("Initial deposit(INR): ");
        String pin  = readString("Set 4-digit PIN    : ");

        int accNo = bankService.createAccount(name, deposit, pin);
        if (accNo > 0) {
            System.out.println("[✓] Account created! Account Number: " + accNo);
        } else {
            System.out.println("[✗] Account creation failed.");
        }
    }

    private static void deposit() {
        System.out.println("\n[ Deposit ]");
        int    accNo  = readInt("Account Number: ");
        String pin    = readString("Enter PIN     : ");
        double amount = readDouble("Amount (INR)  : ");
        bankService.deposit(accNo, pin, amount);
    }

    private static void withdraw() {
        System.out.println("\n[ Withdraw ]");
        int    accNo  = readInt("Account Number: ");
        String pin    = readString("Enter PIN     : ");
        double amount = readDouble("Amount (INR)  : ");
        bankService.withdraw(accNo, pin, amount);
    }

    private static void checkBalance() {
        System.out.println("\n[ Check Balance ]");
        int    accNo = readInt("Account Number: ");
        String pin   = readString("Enter PIN     : ");
        bankService.checkBalance(accNo, pin);
    }

    private static void displayAccount() {
        System.out.println("\n[ Account Details ]");
        int    accNo = readInt("Account Number: ");
        String pin   = readString("Enter PIN     : ");
        bankService.displayAccount(accNo, pin);
    }

    private static void transactionHistory() {
        System.out.println("\n[ Transaction History ]");
        int    accNo = readInt("Account Number: ");
        String pin   = readString("Enter PIN     : ");
        bankService.showTransactionHistory(accNo, pin);
    }

    // ── Input helpers ─────────────────────────────────────────
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("[!] Enter a valid number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("[!] Enter a valid amount: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
