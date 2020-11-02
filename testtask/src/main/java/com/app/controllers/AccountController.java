package com.app.controllers;


import com.app.entities.Account;
import com.app.entities.Credit;
import com.app.entities.Customer;
import com.app.entities.Transfer;
import com.app.repositories.AccountRepository;
import com.app.repositories.CreditRepository;
import com.app.repositories.CustomerRepository;
import com.app.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/accounts")
//RestController includes @Controller and @ResponseBody, this annotation simplifies the controller implementation
@RestController
@Validated
public class AccountController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    CreditRepository creditRepository;


    //@getMapping maps a "/" root path from GET request to the home() method. It return a text
    @GetMapping("/")
    public String home() {
        return "Welcome home!";
    }


    @PostMapping("/createAccount")
    public String createAccount(@RequestParam(name = "customer_id") Long id){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null){
            return "404. Customer not found";
        }
        accountRepository.save(new Account(customer));
        return "200 OK. Account is created.";
    }



    @PostMapping("/transferMoney")
    public String transferMoney(@RequestParam(name = "account_id_from") Long from_id,
                                @RequestParam(name = "account_id_to") Long to_id,
                                @RequestParam(name = "amount") Double money){
        Account from = accountRepository.findAccountById(from_id);
        if(from == null){
            return "404. First account not found";
        }
        Account to = accountRepository.findAccountById(to_id);
        if(to == null){
            return "404. Second account not found";
        }
        if(from.getSaldo() < money){
            return "000. Money not found";
        }
        from.change(-money);
        to.change(money);
        accountRepository.save(from);
        accountRepository.save(to);
        transferRepository.save(new Transfer(from, to, money, new Date()));
        return "200 OK. Transfer is done.";
    }

    @PostMapping("/payoff")
    public String payoff(@RequestParam(name = "account_id") Long account_id,
                         @RequestParam(name = "credit_id") Long credit_id,
                         @RequestParam(name = "amount") Double amount){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            return "404. Account not found";
        }
        Credit credit = creditRepository.findCreditById(credit_id);
        if(credit == null){
            return "404. Credit not found";
        }
        if(account.getSaldo() < amount){
            return "400. Account has less money than expected";
        }
        if(credit.getRemainAmount() < amount){
            return "400. Credit is less than expected";
        }
        account.change(-amount);
        if(credit.pay(amount)){
            credit.getCustomer().setRating(credit.getCustomer().getRating() - 1);
            customerRepository.save(credit.getCustomer());
        }
        accountRepository.save(account);
        creditRepository.save(credit);
        return "200 OK.";
    }




}
