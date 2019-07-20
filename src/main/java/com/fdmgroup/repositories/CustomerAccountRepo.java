package com.fdmgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.entities.Customer;

public interface CustomerAccountRepo extends JpaRepository<Customer, String> {

}
