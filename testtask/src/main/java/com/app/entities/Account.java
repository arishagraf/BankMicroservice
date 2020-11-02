package com.app.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//defines that a class can be mapped to a table (marker)
@Entity
//table annotation specifies the name of the database table to be used for mapping
@Table(name = "ACCOUNTS")
public class Account {
    //id annotation gives our acc an identifier number (primary key)
    @Id
    //GV annotation provides specification of generation strategies for the values of primary key (identity type)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //column annotation is used to specify the mapped column for a persistent property
    @Column(name = "Id", nullable = false)
    private Long id;

    /**
     * JoinColumn annotation allows us to specify the Foreign key column name.
     * our entity Account will have a foreign key "Customer_id" referring to the primary attribute id of our Customer class (relationship)
     */
    @JoinColumn(name = "Customer_Id", nullable = false)
    @ManyToOne
    private Customer customer;

    @Column(name = "Saldo", nullable = false)
    private double saldo;

    @OneToMany(mappedBy = "from")
    private List<Transfer> outComes = new ArrayList<>();

    @OneToMany(mappedBy = "to")
    private List<Transfer> inComes = new ArrayList<>();

    public Account(){

    }

    public Account(Customer customer) {
        this.customer = customer;
        this.saldo = 0;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getSaldo() {
        return saldo;
    }

    public void change(double c){
        this.saldo += c;
    }

    public Long getId() {
        return id;
    }


}
