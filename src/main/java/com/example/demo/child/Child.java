package com.example.demo.child;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "child")
public class Child {

    public enum Gender {

        male ("мужской"),
        female ("женский");

        private String name;
        Gender(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

    }


    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    @Size(min = 2, max= 50, message = "First name should be from 2 to 50 characters")
    private String firstName;

    @Column(name = "year_of_birth")
    @Range(min = 2000, max= 2020, message = "Year of birth should be from 2000 to 2020")
    private int yearOfBirth;

    @Column(name = "gender")
    private Gender gender;

    @Size(min = 4, max= 6, message = "Code should be from 4 to 6 characters")
    @Column(name = "code")
    private String code;

    @Size(min = 2, max= 50, message = "Hobby should be from 2 to 50 characters")
    @Column(name = "hobby")
    private String hobby;

    @Column(name = "age")
    private int age;

    @Column (name = "photo_name")
    private String photoName;

    @Column (name = "illness")
    private String illness;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


   public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public int getAge() {
        return 2020-this.yearOfBirth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Child() {

    }

    public String getIllness() {
        if (illness == null)
            return "Отсутствуют.";
        return illness;
    }

    public String getOriginalIllness()
    {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public Child(String firstName, int yearOfBirth, Gender gender, String code, String hobby, String photoName, String illness) {
        this.firstName = firstName;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.code = code;
        this.hobby = hobby;
        this.age = 2020 - yearOfBirth;
        this.photoName = photoName;
        this.illness = illness;
    }


    @Override
    public String toString() {
        return "Child{" +
                "name=" + firstName +
                ", year of birth='" + yearOfBirth + '\'' +
                '}';
    }
}