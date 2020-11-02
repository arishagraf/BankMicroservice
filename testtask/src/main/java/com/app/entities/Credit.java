package com.app.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
//table annotation specifies the name of the database table to be used for mapping
@Table(name = "CREDITS")
public class Credit {
    //id annotation gives our acc an identifier number (primary key)
    @Id
    //GV annotation provides specification of generation strategies for the values of primary key (identity type)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //column annotation is used to specify the mapped column for a persistent property
    @Column(name = "Id", nullable = false)
    private Long id;
    /**
     * JoinColumn annotation allows us to specify the Foreign key column name.
     * our entity Credit will have a foreign key "Customer_id" referring to the primary attribute id of our Customer class (relationship)
     */
    @JoinColumn(name = "Customer_Id", nullable = false)
    @ManyToOne
    private Customer customer;

    //Temporal annotation must be specified for persistent fields of type util.Date
    @Temporal(TemporalType.DATE)
    @Column(name = "First_Day", nullable = false)
    private Date start;

    //Temporal annotation must be specified for persistent fields of type util.Date
    @Temporal(TemporalType.DATE)
    @Column(name = "Last_Day", nullable = false)
    private Date end;

    @Column(name = "Original_amount", nullable = false)
    private double originalAmount;

    @Column(name = "Remaining_amount", nullable = false)
    private double remainAmount;

    public Credit(){

    }

    public Credit(Customer customer, Date start, Date end, double original_amount) {
        this.customer = customer;
        this.start = start;
        this.end = end;
        this.originalAmount = original_amount;
        this.remainAmount = original_amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public boolean pay(double amount){
        remainAmount -= amount;
        long remain_days = (end.getTime() - new Date().getTime())/ (1000 * 60 * 60 * 24);
        if(remainAmount == 0 && remain_days >= 0){
            return true;
        }
        return false;
    }

}
