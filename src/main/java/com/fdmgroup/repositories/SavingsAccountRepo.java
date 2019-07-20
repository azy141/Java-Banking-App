package com.fdmgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.entities.SavingsAccount;

public interface SavingsAccountRepo extends JpaRepository<SavingsAccount, String>  {

}
