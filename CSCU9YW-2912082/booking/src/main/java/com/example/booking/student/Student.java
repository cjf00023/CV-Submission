package com.example.booking.student;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class Student {
    private String name;
    private String number;
    private String program;
    private String year;

    // Constructor
    public Student(String name, String string, String program, String year) {
        this.name = name;
        this.number = string;
        this.program = program;
        this.year = year;
    }
    //Default constructor
    public Student() 
    {
    	name = "_";
        number = "_";
        program = "_";
        year = "_";
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for number
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // Getter and Setter for program
    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    // Getter and Setter for year
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    // To string of the student class
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", program='" + program + '\'' +
                ", year=" + year +
                '}';
    }

}
