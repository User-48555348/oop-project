/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank;
import java.util.Date;
/**
 *
 * @author hp
 */
public class BankAccount implements bank_interface {
    
    private int accountNumber;
    private String transactionId;
    private Date transactionDate;
    private String type;
    private double WithdrawAmount;
    private double DepositAmount;
    private double TransferAmount;
    private double balance;
    
    public BankAccount(int accountNumber, String type, double initialBalance) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = initialBalance;
    }
    
    public BankAccount() {
        this.balance = 0.0;
    }
    
    @Override
    public void setDeposit() {
        System.out.println("Deposit operation");
    }
    
    public boolean setDeposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            this.DepositAmount = amount;
            this.transactionDate = new Date();
            this.transactionId = generateTransactionId();
            return true;
        }
        return false;
    }
    
    @Override
    public void withdraw() {
        System.out.println("Withdraw operation");
    }
    
    public boolean setWithdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            this.WithdrawAmount = amount;
            this.transactionDate = new Date();
            this.transactionId = generateTransactionId();
            return true;
        }
        return false;
    }
    
    @Override
    public void transfer() {
        System.out.println("Transfer operation");
    }
    
    public boolean setTransfer(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            this.TransferAmount = amount;
            this.transactionDate = new Date();
            this.transactionId = generateTransactionId();
            return true;
        }
        return false;
    }
    
    public boolean transfer(BankAccount targetAccount, double amount) {
        if (this.setTransfer(amount)) {
            targetAccount.setDeposit(amount);
            return true;
        }
        return false;
    }
    
    @Override
    public void check() {
        System.out.println("Balance: " + this.balance);
    }
    
    @Override
    public void loan() {
        System.out.println("Loan operation");
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public Date getDate() {
        return transactionDate;
    }
    
    public double getAmount() {
        return balance;
    }
    
    public double getBalance() {
        return balance;
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
