package com.fdmgroup.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Customers")
public class Customer {
	@Id
	private String username;
	private int customerID;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String password;
	private int ifCurrentAccount;
	private int ifISAAccount;
	private int ifSavingsAccount;
	
	public Customer(String username, String password, String firstName, String lastName, String address, String email,int ifCurrentAccount, int ifSavingsAccount, int ifISAAccount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.ifCurrentAccount=ifCurrentAccount;
		this.ifISAAccount=ifISAAccount;
		this.ifSavingsAccount=ifSavingsAccount;
	}
	
	public int getIfCurrentAccount() {
		return ifCurrentAccount;
	}

	public void setIfCurrentAccount(int ifCurrentAccount) {
		this.ifCurrentAccount = ifCurrentAccount;
	}

	public int getIfISAAccount() {
		return ifISAAccount;
	}

	public void setIfISAAccount(int ifISAAccount) {
		this.ifISAAccount = ifISAAccount;
	}

	public int getIfSavingsAccount() {
		return ifSavingsAccount;
	}

	public void setIfSavingsAccount(int ifSavingsAccount) {
		this.ifSavingsAccount = ifSavingsAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer(){
		
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		Customer other = (Customer) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", address="
				+ address + ", email=" + email + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	
}
