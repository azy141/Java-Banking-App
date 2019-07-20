package com.fdmgroup.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "SavingsAccount")
public class SavingsAccount extends BankAccount implements PersonalSaver {
	@Id
	private String username;
	private double balance;
	public SavingsAccount(String username,int balance) {
		super(username, balance);
		this.setInterest(interestRate);
		this.setAccountType("Savings-Account");
		this.setUsername(username);
		this.username=username;
		this.balance=super.getBalance();
	}
public SavingsAccount(){
	
}

public double getBalance() {
	return balance;
}

public void setBalance(double balance) {
	this.balance = balance;
}

	double interestRate = 14.5;

	public double getInterest() // Getter
	{
		return interestRate;
	}

	public void setInterest(double interestRate_) // Setter
	{
		this.interestRate = interestRate_;
	}

	// Should not be able to withdraw more than the balance available
	public void withdraw(double amount_) {
		if (amount_ > getBalance()) // If the amount entered is more than the
									// available balance then display error
									// message
			System.out
					.println("Amount entered: " + amount_ + " is more than the available balance of: " + getBalance());
		else // Otherwise decrement balance available by amount entered.
			setBalance(getBalance() - amount_);
	}

	// implementing the method from the interface Personal Saver
	// Method: should be able to change the value of the intreset Rate
	public void updateBalanceWithInterestRate() {
		setBalance(getBalance() + (getBalance() * (interestRate / 100)));
	}
}