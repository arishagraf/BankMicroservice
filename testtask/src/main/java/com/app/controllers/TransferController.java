package com.app.controllers;

import com.app.entities.Transfer;
import com.app.repositories.AccountRepository;
import com.app.repositories.CreditRepository;
import com.app.repositories.CustomerRepository;
import com.app.repositories.TransferRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("api/v1/transfers")
@RestController
@Validated
public class TransferController {

    @Autowired
    TransferRepository transferRepository;

    private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");



    @GetMapping("/postingsByDate")
    public String postingsByDate(@RequestParam(name = "date") String date){
        Date d = null;
        try {
            d = df.parse(date);
            String answer = "";
            for(Transfer transfer : transferRepository.findAllByDate(d)){
                answer +=  "from: " + transfer.getFrom().getId() + " to: " + transfer.getTo().getId() +
                        ". Amount: "+ + transfer.getAmount() + "\n";
            }
            return answer;
        } catch (ParseException e) {
            e.printStackTrace();
            return "400 Bad request. Date format is dd-mm-yyyy";
        }
    }

    @GetMapping("/allPostings")
    public String allPostings(){
        String answer = "";
        for(Transfer transfer : transferRepository.findAll()){
            answer +=  "from: " + transfer.getFrom().getId() + " " + transfer.getFrom().getCustomer().getFullName()
                    + " to: " + transfer.getTo().getId() + " " + transfer.getTo().getCustomer().getFullName() +
                    ". Amount: "+ + transfer.getAmount() + ". Date: " + transfer.getDate() + "\n";
        }
        return answer;
    }
}
