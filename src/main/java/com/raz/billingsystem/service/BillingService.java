package com.raz.billingsystem.service;

import com.raz.billingsystem.model.Billing;

import java.util.Date;
import java.util.List;

public interface BillingService {

    void addBill(Billing bill);

    Billing getBillById(int id);

    void addStatus(Billing bill);

    List<Billing> getAllHistory();

    public List<Billing> getClientsByStatus(String status);

    public int getTotalAmountByStatus(String status);

    public boolean markAsPaid(int billingId);

    public List<Billing> getBillingHistoryByDateRange(Date fromDate, Date toDate);

}
