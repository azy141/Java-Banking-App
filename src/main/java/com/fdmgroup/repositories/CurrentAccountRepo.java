package com.fdmgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.entities.CurrentAccount;

public interface CurrentAccountRepo extends JpaRepository<CurrentAccount, String> {

}
