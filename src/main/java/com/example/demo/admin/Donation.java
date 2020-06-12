package com.example.demo.admin;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Size(min = 2, max= 50, message = "Last name should be from 2 to 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 2, max= 50, message = "First name should be from 2 to 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 4, max= 6, message = "Code should be from 4 to 6 characters")
    @Column(name = "code")
    private String code;


    @Size(min = 2, max= 50, message = "Kindness should be from 2 to 50 characters")
    @Column(name = "kindness")
    private String kindness;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Size(min = 2, max= 50, message = "Status should be from 2 to 50 characters")
    @Column(name = "status")
    private String status;


    public LocalDate getIsAcceptedDate() {
        return isAcceptedDate;
    }

    public void setIsAcceptedDate(LocalDate isAcceptedDate) {
        this.isAcceptedDate = isAcceptedDate;
    }

    @Column(name = "is_accepted_date")
    private LocalDate isAcceptedDate;

    public LocalDate getIsDoneDate() {
        return isDoneDate;
    }

    public void setIsDoneDate(LocalDate isDoneDate) {
        this.isDoneDate = isDoneDate;
    }

    @Column(name = "is_done_date")
    private LocalDate isDoneDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKindness() {
        return kindness;
    }

    public void setKindness(String kindness) {
        this.kindness = kindness;
    }

    public Donation() {
    }

    public Donation(String lastName, String firstName, String code, String kindness, String status, LocalDate isAcceptedDate, LocalDate isDoneDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.code = code;
        this.kindness = kindness;
        this.status = status;
        this.isAcceptedDate = isAcceptedDate;
        this.isDoneDate = isDoneDate;
    }
}