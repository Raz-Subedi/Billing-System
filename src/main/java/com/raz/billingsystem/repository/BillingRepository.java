package com.raz.billingsystem.repository;

import com.raz.billingsystem.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BillingRepository extends JpaRepository<Billing,Integer> {
    List<Billing> findAllByStatus(String status);
    List<Billing> findByDateBetween(Date fromDate, Date toDate);

}
