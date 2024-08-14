package org.example.service;

import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    private JpaTransactionManager transactionManager;

    public AccountServiceImpl(){}

    public AccountServiceImpl(JpaTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
    public void transferMoney(int fromAccountId, int toAccountId, double amount) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            Optional<Account> acc1 = accountRepository.findById(fromAccountId);
            Optional<Account> acc2 = accountRepository.findById(toAccountId);

            if (acc1.isPresent() && acc2.isPresent()) {
                Account sourceAccount = acc1.get();
                Account targetAccount = acc2.get();

                double sourceBalance = sourceAccount.getBalance();
                double targetBalance = targetAccount.getBalance();

                if (sourceBalance < amount) {
                    throw new Exception("Amount to transfer is more than balance!");
                }

                sourceAccount.setBalance(sourceBalance - amount);
                targetAccount.setBalance(targetBalance + amount);

                accountRepository.save(sourceAccount);
                accountRepository.save(targetAccount);

                transactionManager.commit(transactionStatus);
            }
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            throw new RuntimeException(e);
        }
    }


    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
    public void depositMoney(int accountId, double amount) {
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            Optional<Account> acc = accountRepository.findById(accountId);
            if(acc.isPresent()){
                Account depositAccount = acc.get();
                depositAccount.setBalance(depositAccount.getBalance() + amount);
                accountRepository.save(depositAccount);
                if(amount < 0) {
                    throw new Exception("The amount must be more than 0");
                }
                transactionManager.commit(status);
            }
        }catch(Exception e){
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getAccount(int accountId) {
        return null;
    }
}
