/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;
import java.util.Scanner;
/**
 *
 * @author hp
 */
public class ATM implements ATM_interface {
    
    private String atmId;
    private String location;
    
    public ATM() {
    }
    
    public ATM(String atmId, String location) {
        this.atmId = atmId;
        this.location = location;
    }
    
    @Override
    public void withdrawCash() {
        System.out.println("Withdraw cash operation");
    }
    
    public void withdrawCash(BankAccount account, double amount) {
        if (account != null && account.setWithdraw(amount)) {
            System.out.println("Withdrawal successful: " + amount);
            System.out.println("Remaining balance: " + account.getBalance());
        } else {
            System.out.println("Withdrawal failed");
        }
    }
    
    @Override
    public void checkBalance() {
        System.out.println("Check balance operation");
    }
    
    public void checkBalance(BankAccount account) {
        if (account != null) {
            System.out.println("Current balance: " + account.getBalance());
        }
    }
    
    @Override
    public void deposit() {
        System.out.println("Deposit operation");
    }
    
    public void deposit(BankAccount account, double amount) {
        if (account != null && account.setDeposit(amount)) {
            System.out.println("Deposit successful: " + amount);
            System.out.println("New balance: " + account.getBalance());
        } else {
            System.out.println("Deposit failed");
        }
    }
    
    public void showATMMenu(Scanner scanner, BankAccount account) {
        boolean usingATM = true;
        
        while (usingATM) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Exit ATM");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    withdrawCash(account, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    deposit(account, depositAmount);
                    break;
                case 4:
                    usingATM = false;
                    System.out.println("Thank you for using our ATM.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    public String getAtmId() {
        return atmId;
    }
    
    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}
