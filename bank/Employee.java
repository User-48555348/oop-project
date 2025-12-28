/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee extends User implements UserAuthentication {
    
    private String position;
    private double salary;
    ArrayList<BankAccount> managedAccounts;
    private static final String SECRET_CODE = "BANK2026";
    
    public Employee() {
        super();
        this.managedAccounts = new ArrayList<>();
    }
    
    public Employee(String id, String name, String email, String username, String password, String position, double salary) {
        super(id, name, email, username, password);
        this.position = position;
        this.salary = salary;
        this.managedAccounts = new ArrayList<>();
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
    
    public static boolean verifyEmployeeCode(String code) {
        return SECRET_CODE.equals(code);
    }
    
    public boolean signup(String employeeCode) {
        if (verifyEmployeeCode(employeeCode)) {
            return signup();
        } else {
            System.out.println("Invalid employee code. Access denied.");
            return false;
        }
    }
    
    @Override
    public void changePassword() {
        System.out.println("Password changed successfully");
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
    
    public static Employee EmployeeRegistration(Scanner scanner, ArrayList<Employee> employees, 
                                                      int employeeIdCounter, UsernameChecker usernameChecker) {
        System.out.println("\n--- Employee Registration ---");
        
        String code = "";
        while (code.trim().isEmpty()) {
            System.out.print("Enter Employee Secret Code: ");
            code = scanner.nextLine();
            if (code.trim().isEmpty()) {
                System.out.println("Code cannot be empty. Please try again.");
            }
        }
        
        if (!Employee.verifyEmployeeCode(code)) {
            System.out.println("Invalid employee code! Access denied.");
            return null;
        }
        
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
        
        String position = "";
        while (position.trim().isEmpty()) {
            System.out.print("Enter Position: ");
            position = scanner.nextLine();
            if (position.trim().isEmpty()) {
                System.out.println("Position cannot be empty. Please try again.");
            }
        }
        
        double salary = 0;
        while (salary <= 0) {
            System.out.print("Enter Salary: ");
            try {
                salary = scanner.nextDouble();
                scanner.nextLine();
                if (salary <= 0) {
                    System.out.println("Salary must be greater than 0. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        
        String id = "EMP" + employeeIdCounter;
        
        Employee employee = new Employee(id, name, email, username, password, position, salary);
        
        if (employee.signup(code)) {
            System.out.println("\n========================================");
            System.out.println("   Employee Registration Successful!");
            System.out.println("========================================");
            System.out.println("Your Account Details:");
            System.out.println("Employee ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Username: " + username);
            System.out.println("Position: " + position);
            System.out.println("Salary: " + salary);
            System.out.println("========================================");
            System.out.println("Please save this information for future reference.");
            return employee;
        } else {
            System.out.println("Registration failed.");
            return null;
        }
    }
    
    public static void EmployeeLogin(Scanner scanner, ArrayList<Employee> employees, 
                                          ArrayList<Customer> customers, ArrayList<BankAccount> accounts) {
        System.out.println("\n--- Employee Login ---");
        
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
        
        Employee loggedEmployee = null;
        
        for (Employee e : employees) {
            if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
                loggedEmployee = e;
                break;
            }
        }
        
        if (loggedEmployee != null && loggedEmployee.login()) {
            System.out.println("\nLogin successful! Welcome " + loggedEmployee.getName());
            loggedEmployee.showEmployeeMenu(scanner, customers, accounts);
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    public void showEmployeeMenu(Scanner scanner, ArrayList<Customer> customers, ArrayList<BankAccount> accounts) {
        boolean loggedIn = true;
        
        while (loggedIn) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. View All Customers");
            System.out.println("2. View Customer Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllCustomers(customers);
                    break;
                case 2:
                    viewSpecificCustomerAccount(scanner, customers);
                    break;
                case 3:
                    viewAllAccounts(accounts);
                    break;
                case 4:
                    changePasswordMenu(scanner);
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    private void viewAllCustomers(ArrayList<Customer> customers) {
        System.out.println("\n--- All Customers ---");
        
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        
        for (Customer c : customers) {
            System.out.println("Name: " + c.getName());
            System.out.println("Username: " + c.getUsername());
            System.out.println("Customer ID: " + c.getCustomerID());
            System.out.println("Number of Accounts: " + c.getAccounts().size());
            System.out.println("---");
        }
    }
    
    private void viewSpecificCustomerAccount(Scanner scanner, ArrayList<Customer> customers) {
        System.out.print("\nEnter Customer Username: ");
        String username = scanner.nextLine();
        
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                customer = c;
                break;
            }
        }
        
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        System.out.println("\n--- Customer: " + customer.getName() + " ---");
        
        if (customer.getAccounts().isEmpty()) {
            System.out.println("Customer has no accounts.");
            return;
        }
        
        for (BankAccount account : customer.getAccounts()) {
            this.viewAccount(account);
            System.out.println("---");
        }
    }
    
    private void viewAllAccounts(ArrayList<BankAccount> accounts) {
        System.out.println("\n--- All Bank Accounts ---");
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the system.");
            return;
        }
        
        for (BankAccount account : accounts) {
            this.viewAccount(account);
            System.out.println("---");
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
    
    public String getName() {
        return super.getName();
    }
    
    public String getEmail() {
        return super.getEmail();
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public void viewAccount(BankAccount account) {
        if (account != null) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Type: " + account.getType());
        }
    }
    
    public void addCustomer(Customer customer) {
        if (customer != null) {
            System.out.println("Customer " + customer.getName() + " added successfully");
        }
    }
    
    public void removeCustomer(Customer customer) {
        if (customer != null) {
            System.out.println("Customer " + customer.getName() + " removed");
        }
    }
    
    public BankAccount getEmployeeId() {
        if (!managedAccounts.isEmpty()) {
            return managedAccounts.get(0);
        }
        return null;
    }
    
    public void addManagedAccount(BankAccount account) {
        if (account != null && !managedAccounts.contains(account)) {
            managedAccounts.add(account);
        }
    }
    
    public ArrayList<BankAccount> getManagedAccounts() {
        return managedAccounts;
    }
    
    public interface UsernameChecker {
        boolean isUsernameTaken(String username);
    }
}
