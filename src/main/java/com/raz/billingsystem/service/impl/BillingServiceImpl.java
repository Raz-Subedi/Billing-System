package com.raz.billingsystem.service.impl;

import com.raz.billingsystem.model.Billing;
import com.raz.billingsystem.repository.BillingRepository;
import com.raz.billingsystem.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billRepo;


    @Override
    public void addBill(Billing bill) {
        billRepo.save(bill);
    }

    @Override
    public Billing getBillById(int id) {
        return billRepo.findById(id).orElse(null);
    }

    @Override
    public void addStatus(Billing bill) {
        billRepo.save(bill);
    }

    @Override
    public List<Billing> getAllHistory() {
        return billRepo.findAll();
    }

    @Override
    public List<Billing> getClientsByStatus(String status) {
        return billRepo.findAllByStatus(status);
    }

    public int getTotalAmountByStatus(String status) {
        return (int) billRepo.findAllByStatus(status).stream()
                .mapToLong(Billing::getTotal)
                .sum();
    }

    public boolean markAsPaid(int billingId) {
        Optional<Billing> billingOpt = billRepo.findById(billingId);
        if (billingOpt.isPresent()) {
            Billing billing = billingOpt.get();
            billing.setStatus("Paid");
            billRepo.save(billing);
            return true;
        }
        return false;
    }

    @Override
    public List<Billing> getBillingHistoryByDateRange(Date fromDate, Date toDate) {
        return billRepo.findByDateBetween(fromDate, toDate);
    }
}



