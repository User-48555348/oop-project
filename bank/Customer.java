/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User implements UserAuthentication {
    
    private String customerID;
    ArrayList<BankAccount> accounts;
    
    public Customer() {
        super();
        this.accounts = new ArrayList<>();
    }
    
    public Customer(String id, String name, String email, String username, String password, String customerID) {
        super(id, name, email, username, password);
        this.customerID = customerID;
        this.accounts = new ArrayList<>();
    }
    
    @Override
    public boolean login() {
        if (getUsername() != null && getPassword() != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean signup() {
        if (getUsername() != null && getPassword() != null && getEmail() != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public void changePassword() {
        System.out.println("Password changed");
    }
    
    public boolean changePassword(String oldPass, String newPass) {
        if (getPassword().equals(oldPass)) {
            setPassword(newPass);
            System.out.println("Password updated successfully!");
            return true;
        } else {
            System.out.println("Old password incorrect.");
            return false;
        }
    }
    
    // MOVED FROM BankSystem: Customer registration logic
    public static Customer customerRegistration(Scanner scanner, ArrayList<Customer> customers, 
                                                      int customerIdCounter, UsernameChecker usernameChecker) {
        System.out.println("\n--- Customer Registration ---");
        
        String name = "";
        while (name.trim().isEmpty()) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        }
        
        String email = "";
        while (email.trim().isEmpty()) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
            }
        }
        
        String username = "";
        while (username.trim().isEmpty()) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            if (username.trim().isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            } else if (usernameChecker.isUsernameTaken(username)) {
                System.out.println("Username already exists. Try another one.");
                username = "";
            }
        }
        
        String password = "";
        while (password.trim().isEmpty()) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }
        
        String confirmPassword = "";
        while (!password.equals(confirmPassword)) {
            System.out.print("Confirm Password: ");
            confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords don't match. Please try again.");
            }
        }
        
        String id = "ID" + customerIdCounter;
        String customerID = "CUST" + (customers.size() + 1);
        
        Customer customer = new Customer(id, name, email, username, password, customerID);
        
        if (customer.signup()) {
            System.out.println("\n========================================");
            System.out.println("   Registration Successful!");
            System.out.println("========================================");
            System.out.println("Your Account Details:");
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Username: " + username);
            System.out.println("Customer ID: " + customerID);
            System.out.println("========================================");
            System.out.println("Please save this information for future reference.");
            return customer;
        } else {
            System.out.println("Registration failed.");
            return null;
        }
    }
    
    // MOVED FROM BankSystem: Customer login logic
    public static void CustomerLogin(Scanner scanner, ArrayList<Customer> customers, 
                                          ArrayList<BankAccount> accounts) {
        System.out.println("\n--- Customer Login ---");
        
        String username = "";
        while (username.trim().isEmpty()) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            if (username.trim().isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            }
        }
        
        String password = "";
        while (password.trim().isEmpty()) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }
        
        Customer loggedCustomer = null;
        
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                loggedCustomer = c;
                break;
            }
        }
        
        if (loggedCustomer != null && loggedCustomer.login()) {
            System.out.println("\nLogin successful! Welcome back " + loggedCustomer.getName());
            loggedCustomer.showCustomerMenu(scanner, accounts);
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    public void showCustomerMenu(Scanner scanner, ArrayList<BankAccount> globalAccounts) {
        boolean loggedIn = true;
        
        while (loggedIn) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Create New Account");
            System.out.println("2. View My Accounts");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Check Balance");
            System.out.println("7. Change Password");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    createAccount(scanner, globalAccounts);
                    break;
                case 2:
                    viewAccounts();
                    break;
                case 3:
                    depositMoney(scanner);
                    break;
                case 4:
                    withdrawMoney(scanner);
                    break;
                case 5:
                    transferMoney(scanner, globalAccounts);
                    break;
                case 6:
                    checkBalance(scanner);
                    break;
                case 7:
                    changePasswordMenu(scanner);
                    break;
                case 8:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    private void createAccount(Scanner scanner, ArrayList<BankAccount> globalAccounts) {
        System.out.println("\n--- Create Bank Account ---");
        
        int accountNumber = 100000 + (int)(Math.random() * 900000);
        
        boolean exists = false;
        for (BankAccount acc : globalAccounts) {
            if (acc.getAccountNumber() == accountNumber) {
                exists = true;
                break;
            }
        }
        
        while (exists) {
            accountNumber = 100000 + (int)(Math.random() * 900000);
            exists = false;
            for (BankAccount acc : globalAccounts) {
                if (acc.getAccountNumber() == accountNumber) {
                    exists = true;
                    break;
                }
            }
        }
        
        String type = "";
        while (type.trim().isEmpty()) {
            System.out.print("Enter Account Type (Savings/Checking): ");
            type = scanner.nextLine();
            if (type.trim().isEmpty()) {
                System.out.println("Account type cannot be empty. Please try again.");
            } else if (!type.equalsIgnoreCase("Savings") && !type.equalsIgnoreCase("Checking")) {
                System.out.println("Invalid account type. Please enter 'Savings' or 'Checking'.");
                type = "";
            }
        }
        
        double initialBalance = -1;
        while (initialBalance < 0) {
            System.out.print("Enter Initial Deposit (minimum 100): ");
            try {
                initialBalance = scanner.nextDouble();
                scanner.nextLine();
                if (initialBalance < 100) {
                    System.out.println("Initial deposit must be at least 100. Please try again.");
                    initialBalance = -1;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                initialBalance = -1;
            }
        }
        
        BankAccount account = new BankAccount(accountNumber, type, initialBalance);
        this.addAccount(account);
        globalAccounts.add(account);
        
        System.out.println("\n========================================");
        System.out.println("   Account Created Successfully!");
        System.out.println("========================================");
        System.out.println("Your Account Details:");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + type);
        System.out.println("Initial Balance: " + initialBalance);
        System.out.println("========================================");
        System.out.println("Please save your account number for future transactions.");
    }
    
    private void viewAccounts() {
        System.out.println("\n--- Your Accounts ---");
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        for (BankAccount account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Type: " + account.getType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("---");
        }
    }
    
    private void depositMoney(Scanner scanner) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Create an account first.");
            return;
        }
        
        System.out.print("\nEnter Account Number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();
        
        BankAccount account = findAccount(accountNumber);
        
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            if (account.setDeposit(amount)) {
                System.out.println("Deposit successful!");
                System.out.println("New Balance: " + account.getBalance());
            } else {
                System.out.println("Deposit failed.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
    
    private void withdrawMoney(Scanner scanner) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.print("\nEnter Account Number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();
        
        BankAccount account = findAccount(accountNumber);
        
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            if (account.setWithdraw(amount)) {
                System.out.println("Withdrawal successful!");
                System.out.println("Remaining Balance: " + account.getBalance());
            } else {
                System.out.println("Insufficient funds or invalid amount.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
    
    private void transferMoney(Scanner scanner, ArrayList<BankAccount> globalAccounts) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.print("\nEnter Your Account Number: ");
        int fromAccountNumber = scanner.nextInt();
        scanner.nextLine();
        
        BankAccount fromAccount = findAccount(fromAccountNumber);
        
        if (fromAccount == null) {
            System.out.println("Your account not found.");
            return;
        }
        
        System.out.print("Enter Recipient Account Number: ");
        int toAccountNumber = scanner.nextInt();
        scanner.nextLine();
        
        BankAccount toAccount = findAccountInGlobal(toAccountNumber, globalAccounts);
        
        if (toAccount == null) {
            System.out.println("Recipient account not found.");
            return;
        }
        
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        if (fromAccount.transfer(toAccount, amount)) {
            System.out.println("Transfer successful!");
            System.out.println("Your New Balance: " + fromAccount.getBalance());
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }
    
    private void checkBalance(Scanner scanner) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.print("\nEnter Account Number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();
        
        BankAccount account = findAccount(accountNumber);
        
        if (account != null) {
            account.check();
        } else {
            System.out.println("Account not found.");
        }
    }
    
    private void changePasswordMenu(Scanner scanner) {
        System.out.print("\nEnter Old Password: ");
        String oldPass = scanner.nextLine();
        
        System.out.print("Enter New Password: ");
        String newPass = scanner.nextLine();
        
        System.out.print("Confirm New Password: ");
        String confirmPass = scanner.nextLine();
        
        if (!newPass.equals(confirmPass)) {
            System.out.println("New passwords don't match.");
            return;
        }
        
        changePassword(oldPass, newPass);
    }
    
    private BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
    
    private BankAccount findAccountInGlobal(int accountNumber, ArrayList<BankAccount> globalAccounts) {
        for (BankAccount account : globalAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
    
    public void newAccount(BankAccount account) {
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
            System.out.println("New account created");
        }
    }
    
    public String getName() {
        return super.getName();
    }
    
    public String getEmail() {
        return super.getEmail();
    }
    
    public String getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
    
    public void addAccount(BankAccount account) {
        if (account != null) {
            accounts.add(account);
        }
    }
    
    public void removeAccount(BankAccount account) {
        if (account != null) {
            accounts.remove(account);
        }
    }
    
    // Interface to allow Customer methods to check username availability
    public interface UsernameChecker {
        boolean isUsernameTaken(String username);
    }
}
