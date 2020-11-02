package com.app.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
//table annotation specifies the name of the database table to be used for mapping
@Table(name = "Transfers")
public class Transfer {
    //id annotation gives our acc an identifier number (primary key)
    @Id
    //GV annotation provides specification of generation strategies for the values of primary key (identity type)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //column annotation is used to specify the mapped column for a persistent property
    @Column(name = "Id", nullable = false)
    private Long id;

    /**
     * JoinColumn annotation allows us to specify the Foreign key column name.
     * our entity Transfer will have a foreign key "From_account_id" referring to the primary attribute id of our Account class (relationship)
     */
    @JoinColumn(name = "From_account_id", nullable = false)
    @ManyToOne
    private Account from;

   // our entity Transfer will have a foreign key "To_account_id" referring to the primary attribute id of our Account class (relationship)
    @JoinColumn(name = "To_account_id", nullable = false)
    @ManyToOne
    private Account to;

    @Column(name = "Amount", nullable = false)
    private double amount;

    //Temporal annotation must be specified for persistent fields of type util.Date
    @Temporal(TemporalType.DATE)
    @Column(name = "Date", nullable = false)
    private Date date;

    public Transfer(){

    }

    public Transfer(Account from, Account to, double amount, Date date) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
