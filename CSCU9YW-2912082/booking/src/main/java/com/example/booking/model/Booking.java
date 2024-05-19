package com.example.booking.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.booking.student.Student;
@Entity
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookingid;

	private String booking;
	private String professor;
	private String datetime;
	
	private Student student;
	//Default to prevent errors with null value fields
	public Booking() {
	    this.bookingid = "B" + String.format("%03d", bookingid);
	    this.booking = "_";
	    this.professor = "_";
	    this.datetime = "_";
	    this.student = new Student();
	}

	//Standard constructor
	public Booking(String bookingid,String booking,String professor,String datetime,String name,String number,String program,String year) 
	{
		this.bookingid = bookingid;
		this.booking = booking;
		this.professor = professor;
		this.datetime = datetime;
		this.student = new Student(name,number,program,year);
	}
	//constructor for copying
	public Booking(Booking that)
	{
		this.bookingid = that.bookingid;
		this.booking = that.booking;
		this.student = that.student;
		this.professor = that.professor;
		this.datetime = that.datetime;
	}
	 // Getter and Setter for bookingId
    public String getBookingId() {
        return bookingid;
    }

    public void setBookingId(String bookingId) {
        this.bookingid = bookingId;
    }

    // Getter and Setter for booking
    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    // Getter and Setter for student
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Getter and Setter for professor
    public String getProfessor() {
        return professor;
    }

    public  void setProfessor(String professor) {
        this.professor = professor;
    }

    // Getter and Setter for datetime
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }	
    @Override
    public String toString() {
        return "Booking{" +
                "Bookingid=\"" + bookingid + "\"," +
                "Booking=\"" + booking + "\"," +
                "Professor=\"" + professor + "\"," +
                "Datetime=\"" + datetime + "\"," +
                "Name=\"" + student.getName() + "\"," +
                "Number=\"" + student.getNumber() + "\"," +
                "Program=\"" + student.getProgram() + "\"," +
                "Year=\"" + student.getYear() + "\"}";
    }

}
