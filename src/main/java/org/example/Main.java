package org.example;

import org.example.config.JPAConfig;
import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;
import org.example.service.AccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JPAConfig.class);
    static AccountRepository accountRepository = (AccountRepository) applicationContext.getBean("accountRepository");
    static AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
    public static void main(String[] args) {
//        createNewAccount();
//       accountService.transferMoney(1,2,500);
        accountService.depositMoney(1,500);
    }

    public static void createNewAccount(){
        Account  acc1 = new Account("Le Hoang Long",50.0, LocalDate.parse("2024-08-14"),false);
        Account  acc2 = new Account("Nguyen Le Uyen",90.0, LocalDate.parse("2024-08-10"),false);
        accountRepository.save(acc1);
        accountRepository.save(acc2);
    }

}