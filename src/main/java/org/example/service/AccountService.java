package org.example.service;

import org.example.entity.Account;


public interface AccountService {
    void transferMoney(int fromAccountId, int toAccountId, double amount);
    void depositMoney(int accountId, double amount);
    Account getAccount(int accountId);
}
