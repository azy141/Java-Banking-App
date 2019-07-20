package com.fdmgroup.entities;

import javax.persistence.MappedSuperclass;

//@MappedSuperclass
abstract class BankAccount {
	
	
	private String username;
	
	
	private int sortCode;
	private double balance;
	private String accountType;
	private String password;

	public BankAccount(String username,double balance) {
		this.username = username;;
		this.balance = balance;
	}
	
	public BankAccount(){
		
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSortCode() {
		return sortCode;
	}

	public void setSortCode(int sortCode) {
		if (sortCode == 6)
			this.sortCode = sortCode;
		else
			System.out.println("Sort Code must be 6 digits!");
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void deposit(Double amount) {
		balance += amount;
	}

	public void withdraw(Double amount) {
		balance -= amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [username=" + username + ", sortCode=" + sortCode + ", balance=" + balance
				+ ", accountType=" + accountType + "]";
	}




	

}