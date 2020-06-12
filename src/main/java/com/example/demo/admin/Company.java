package com.example.demo.admin;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id")
    private long id;


    @Size(min = 2, max= 50, message = "Name should be from 2 to 50 characters")
    @Column(name = "name")
    private String name;

    @Size(min = 16, max= 25, message = "First name should be from 16 to 20 characters")
    @Column(name = "card_number")
    private String cardNumber;

    @Pattern(regexp = "^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$",
            message = "Invalid phone number")
    @Column(name = "phone")
    private String phone;

    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
            message = "Invalid email format")
    @Column(name = "email")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company(long id, String name, String cardNumber, String phone, String email) {
        this.id = id;
        this.name=name;
        this.cardNumber = cardNumber;
        this.phone=phone;
        this.email=email;
    }

    public Company() {

    }
}