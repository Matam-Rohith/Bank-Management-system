#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_ACCOUNTS 100
#define FILENAME "bank_data.dat"

// Structure to hold account details
struct Account {
    int accountNumber;
    char name[50];
    float balance;
    char pin[5];
};

// Function prototypes
void createAccount(struct Account accounts[], int *count);
void deposit(struct Account accounts[], int count);
void withdraw(struct Account accounts[], int count);
void checkBalance(struct Account accounts[], int count);
void displayAccount(struct Account accounts[], int count);
int findAccount(struct Account accounts[], int count, int accNo);
void saveAccounts(struct Account accounts[], int count);
void loadAccounts(struct Account accounts[], int *count);

int main() {
    struct Account accounts[MAX_ACCOUNTS];
    int count = 0;
    int choice;

    // Load existing accounts from file
    loadAccounts(accounts, &count);

    while (1) {
        printf("\n=== Bank Management System ===\n");
        printf("1. Create Account\n");
        printf("2. Deposit\n");
        printf("3. Withdraw\n");
        printf("4. Check Balance\n");
        printf("5. Display Account Details\n");
        printf("6. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                createAccount(accounts, &count);
                break;
            case 2:
                deposit(accounts, count);
                break;
            case 3:
                withdraw(accounts, count);
                break;
            case 4:
                checkBalance(accounts, count);
                break;
            case 5:
                displayAccount(accounts, count);
                break;
            case 6:
                saveAccounts(accounts, count);
                printf("Thank you for using the Bank Management System!\n");
                exit(0);
            default:
                printf("Invalid choice! Please try again.\n");
        }
    }
    return 0;
}

void createAccount(struct Account accounts[], int *count) {
    if (*count >= MAX_ACCOUNTS) {
        printf("Cannot create more accounts. Storage full!\n");
        return;
    }

    struct Account newAccount;
    newAccount.accountNumber = 1000 + *count + 1; // Simple account number generation
    printf("Enter name: ");
    scanf(" %[^\n]", newAccount.name);
    printf("Enter initial deposit (minimum 1000): ");
    scanf("%f", &newAccount.balance);
    if (newAccount.balance < 1000) {
        printf("Initial deposit must be at least 1000!\n");
        return;
    }
    printf("Enter 4-digit PIN: ");
    scanf("%s", newAccount.pin);
    if (strlen(newAccount.pin) != 4) {
        printf("PIN must be exactly 4 digits!\n");
        return;
    }

    accounts[*count] = newAccount;
    (*count)++;
    saveAccounts(accounts, *count);
    printf("Account created successfully! Account Number: %d\n", newAccount.accountNumber);
}

int findAccount(struct Account accounts[], int count, int accNo) {
    for (int i = 0; i < count; i++) {
        if (accounts[i].accountNumber == accNo) {
            return i;
        }
    }
    return -1;
}

void deposit(struct Account accounts[], int count) {
    int accNo;
    float amount;
    char pin[5];
    
    printf("Enter account number: ");
    scanf("%d", &accNo);
    printf("Enter PIN: ");
    scanf("%s", pin);

    int index = findAccount(accounts, count, accNo);
    if (index == -1) {
        printf("Account not found!\n");
        return;
    }
    if (strcmp(accounts[index].pin, pin) != 0) {
        printf("Incorrect PIN!\n");
        return;
    }

    printf("Enter amount to deposit: ");
    scanf("%f", &amount);
    if (amount <= 0) {
        printf("Invalid amount!\n");
        return;
    }

    accounts[index].balance += amount;
    saveAccounts(accounts, count);
    printf("Deposit successful! New balance: %.2f\n", accounts[index].balance);
}

void withdraw(struct Account accounts[], int count) {
    int accNo;
    float amount;
    char pin[5];
    
    printf("Enter account number: ");
    scanf("%d", &accNo);
    printf("Enter PIN: ");
    scanf("%s", pin);

    int index = findAccount(accounts, count, accNo);
    if (index == -1) {
        printf("Account not found!\n");
        return;
    }
    if (strcmp(accounts[index].pin, pin) != 0) {
        printf("Incorrect PIN!\n");
        return;
    }

    printf("Enter amount to withdraw: ");
    scanf("%f", &amount);
    if (amount <= 0 || amount > accounts[index].balance) {
        printf("Invalid amount or insufficient balance!\n");
        return;
    }

    accounts[index].balance -= amount;
    saveAccounts(accounts, count);
    printf("Withdrawal successful! New balance: %.2f\n", accounts[index].balance);
}

void checkBalance(struct Account accounts[], int count) {
    int accNo;
    char pin[5];
    
    printf("Enter account number: ");
    scanf("%d", &accNo);
    printf("Enter PIN: ");
    scanf("%s", pin);

    int index = findAccount(accounts, count, accNo);
    if (index == -1) {
        printf("Account not found!\n");
        return;
    }
    if (strcmp(accounts[index].pin, pin) != 0) {
        printf("Incorrect PIN!\n");
        return;
    }

    printf("Current balance: %.2f\n", accounts[index].balance);
}

void displayAccount(struct Account accounts[], int count) {
    int accNo;
    char pin[5];
    
    printf("Enter account number: ");
    scanf("%d", &accNo);
    printf("Enter PIN: ");
    scanf("%s", pin);

    int index = findAccount(accounts, count, accNo);
    if (index == -1) {
        printf("Account not found!\n");
        return;
    }
    if (strcmp(accounts[index].pin, pin) != 0) {
        printf("Incorrect PIN!\n");
        return;
    }

    printf("\nAccount Details:\n");
    printf("Account Number: %d\n", accounts[index].accountNumber);
    printf("Name: %s\n", accounts[index].name);
    printf("Balance: %.2f\n", accounts[index].balance);
}

void saveAccounts(struct Account accounts[], int count) {
    FILE *file = fopen(FILENAME, "wb");
    if (file == NULL) {
        printf("Error saving data!\n");
        return;
    }
    fwrite(&count, sizeof(int), 1, file);
    fwrite(accounts, sizeof(struct Account), count, file);
    fclose(file);
}

void loadAccounts(struct Account accounts[], int *count) {
    FILE *file = fopen(FILENAME, "rb");
    if (file == NULL) {
        *count = 0;
        return;
    }
    fread(count, sizeof(int), 1, file);
    fread(accounts, sizeof(struct Account), *count, file);
    fclose(file);
}
