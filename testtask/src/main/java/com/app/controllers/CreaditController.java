package com.app.controllers;

import com.app.entities.Credit;
import com.app.entities.Customer;
import com.app.repositories.AccountRepository;
import com.app.repositories.CreditRepository;
import com.app.repositories.CustomerRepository;
import com.app.repositories.TransferRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RequestMapping("api/v1/credits")
@RestController
@Validated
public class CreaditController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CreditRepository creditRepository;

    private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");


    //create a cderit for a customer
    @PostMapping("/createCredit")
    public String createCredit(@RequestParam(name = "customer_id") Long id,
                               @RequestParam(name = "endDate") String endDate,
                               @RequestParam(name = "amount") Double amount){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null){
            return "404. Customer not found";
        }
        if(amount <= 0){
            return "404. Amount must be more than 0.";
        }
        Date date = null;
        try {
            date = df.parse(endDate);
            Date today = new Date();
            if(today.after(date)){
                return "400 Bad request. End date should be not sooner than today.";
            }
            creditRepository.save(new Credit(customer, today, date, amount));
            return "200 OK";
        } catch (ParseException e) {
            e.printStackTrace();
            return "400 Bad request. Date format is dd-mm-yyyy";
        }
    }
}
