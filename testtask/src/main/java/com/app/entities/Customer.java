package com.app.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//table annotation specifies the name of the database table to be used for mapping
@Table(name = "CUSTOMERS")
public class Customer {
    //id annotation gives our acc an identifier number (primary key)
    @Id
    //GV annotation provides specification of generation strategies for the values of primary key (identity type)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //column annotation is used to specify the mapped column for a persistent property
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Full_Name", length = 64, nullable = false)
    private String fullName;

    //Temporal annotation must be specified for persistent fields of type util.Date
    @Temporal(TemporalType.DATE)
    @Column(name = "Date_Of_Birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "Address", length = 128, nullable = false)
    private String address;

    @Column(name = "rating", nullable = false)
    private int rating;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Credit> credits = new ArrayList<>();

    public Customer(){

    }

    public Customer(String fullName, Date dateOfBirth, String address) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.rating = 2;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public int getRating() {
        return rating;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setRating(int r) {
        if(r<1){
            r = 1;
        }
        this.rating = r;
    }
}
