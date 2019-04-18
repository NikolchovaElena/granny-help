package com.example.granny.repository;

import com.example.granny.domain.entities.BillingDetails;
import com.example.granny.domain.entities.Order;
import com.example.granny.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<BillingDetails,Integer> {



}
