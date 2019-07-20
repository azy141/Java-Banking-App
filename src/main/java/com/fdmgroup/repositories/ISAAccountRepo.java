package com.fdmgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.entities.ISAAccount;

public interface ISAAccountRepo extends JpaRepository<ISAAccount, String>  {

}
