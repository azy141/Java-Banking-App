package com.fdmgroup.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CurrentAccount")
public class CurrentAccount extends BankAccount implements EverydayAccount {
	@Id
	private String username;
	private double balance;
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	private double overDraftAmount; 
	private double overDraftInterest;
	double overDraftAmountMax = 2500;
	

	public CurrentAccount(String username,double balance) {
		super(username, balance);
		this.setAccountType("Current-Account");
		this.username=username;
		this.balance=super.getBalance();
		this.overDraftAmount=overDraftAmount;
		if (overDraftAmount > overDraftAmountMax) {
			
			this.setOverDraftAmount(2000);
		} else {
			this.setOverDraftAmount(overDraftAmount);
		}

	}
	
	public CurrentAccount(){
		
	}

	public double getOverDraftAmount() {
		return overDraftAmount;
	}

	public void setOverDraftAmount(double overDraftAmount) {
		this.overDraftAmount = overDraftAmount;
	}

	public double getOverDraftInterest() {
		return overDraftInterest;
	}

	public void setOverDraftInterest(double overDraftInterest) {
		this.overDraftInterest = overDraftInterest;
	}

	@Override
	public void withdraw(Double amount) {
		if ((getBalance() + overDraftAmount - amount) < 0)
		setBalance(getBalance() - amount);
	}

	// UpdateBalance with the overdraft charges
	public void applyOverDraftChargesInterest() {
		if (getBalance() < 0)
			setBalance(getBalance() + (getBalance() * overDraftInterest));
	}

	@Override
	public String toString() {
		return "CurrentAccount [username=" + username + ", balance=" + balance + ", overDraftAmount=" + overDraftAmount
				+ ", overDraftInterest=" + overDraftInterest + ", overDraftAmountMax=" + overDraftAmountMax + "]";
	}

}
