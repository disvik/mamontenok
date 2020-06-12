package com.example.demo.admin;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long user_id;

    @Size(min = 2, max= 50, message = "Last name should be from 2 to 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 2, max= 50, message = "First name should be from 2 to 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max= 50, message = "Patronymic should be from 2 to 50 characters")
    @Column(name = "patronymic")
    private String patronymic;

    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
            message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$",
            message = "Invalid phone number")
    @Column(name = "phone")
    private String phone;


    @Size(min = 6, max= 50, message = "Password should be from 6 to 50 characters")
    @Column(name = "passwd")
    private String password;


    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Manager() {
    }

    public Manager(String lastName, String firstName, String patronymic, String email, String phone, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}