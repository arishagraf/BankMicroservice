package com.app.controllers;

import com.app.entities.Account;
import com.app.entities.Credit;
import com.app.entities.Customer;
import com.app.repositories.AccountRepository;
import com.app.repositories.CreditRepository;
import com.app.repositories.CustomerRepository;
import com.app.repositories.TransferRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("api/v1/customers")
@RestController
@Validated
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CreditRepository creditRepository;

    private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    //@PostMapping maps POST requests onto createUser() method and creates User with fullname, birthdate, address
    @PostMapping("/createUser")
    public String createUser(@RequestParam(name = "fullname") String name,
                             @RequestParam(name = "dateOfBirth") String dateString,
                             @RequestParam(name = "adress") String adress){
        Date date = null;
        try {
            date = df.parse(dateString);
            customerRepository.save(new Customer(name, date, adress)); //save is CRUDRepository method
            return "200 OK";
        } catch (ParseException e) {
            e.printStackTrace();
            return "400 Bad request. Date format is dd-mm-yyyy";
        }
    }

    //Sort customer
    @GetMapping("/namesAndAdressesByLastName")
    public String namesAndAdressesByLastName(@RequestParam(name = "lastname") String lastname, @RequestParam(name = "sort", required = false, defaultValue = "false") Boolean sort,
                                             @RequestParam(name = "sortBy", required = false, defaultValue = "byFirstName") String sortType){
        List<Customer> customers = customerRepository.findCustomerByFullNameEndsWith(lastname);
        if(sort){
            if(sortType.equals("byAdress")){
                customers = customers.stream()
                        .sorted(Comparator.comparing(Customer::getAddress))
                        .collect(Collectors.toList());
            }
            else{
                customers = customers.stream()
                        .sorted(Comparator.comparing(Customer::getFullName))
                        .collect(Collectors.toList());
            }
        }
        String answer = "";
        for(Customer customer:customers){
            answer += customer.getFullName() + ". Adress:" + customer.getAddress() + "\n";
        }
        return answer;
    }

    @GetMapping("/customerAccounts")
    public String customerAccounts(@RequestParam(name = "customer_id") Long id){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null){
            return "404. Customer not found";
        }
        String answer = "";
        for (Account account : customer.getAccounts()){
            answer += account.getId() + ". Balance: " + account.getSaldo() + "\n";
        }
        return answer;
    }


    @GetMapping("/customerCredits")
    public String customerCredits(@RequestParam(name = "customer_id") Long id){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null){
            return "404. Customer not found";
        }
        String answer = "";
        for (Credit credit : customer.getCredits()){
            long remain_days = (credit.getEnd().getTime() - new Date().getTime())/ (1000 * 60 * 60 * 24);
            answer +=  "Original term: " + credit.getEnd() + ". Remaining days: " + remain_days +
                    ". Original amount: " + credit.getOriginalAmount() + ". Remaining amount: " +
                    credit.getRemainAmount() + "\n";
        }
        return answer;
    }

    @GetMapping("/customerBalance")
    public String customerBalance(@RequestParam(name = "customer_id") Long customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            return "404. Customer not found";
        }
        double saldo = 0;
        for(Account account : customer.getAccounts()){
            saldo += account.getSaldo();
        }
        double credit = 0;
        for(Credit cr : customer.getCredits()){
            credit += cr.getRemainAmount();
        }
        return "Total balance: " + saldo + ". Credit to pay: " + credit;
    }

    @GetMapping("/balance")
    public String balance(){
        String answer = "";
        for(Customer customer : customerRepository.findAll()){
            double saldo = 0;
            for(Account account : customer.getAccounts()){
                saldo += account.getSaldo();
            }
            double credit = 0;
            for(Credit cr : customer.getCredits()){
                credit += cr.getRemainAmount();
            }
            answer += customer.getFullName() + ". Total balance: " + saldo + ". Credit to pay: " + credit + "\n";
        }
        return answer;
    }


    @GetMapping("/expiredCredits")
    public String expiredCredits(){
        String answer = "";
        for(Credit credit : creditRepository.findCreditsByRemainAmountIsGreaterThanAndEndBefore(0.0, new Date())){
            answer += credit.getCustomer().getFullName() + ". Original: " + credit.getOriginalAmount() + ". Current: " +
                    credit.getRemainAmount() + "\n";
            credit.getCustomer().setRating(4);
            customerRepository.save(credit.getCustomer());
        }
        return answer;
    }

}
