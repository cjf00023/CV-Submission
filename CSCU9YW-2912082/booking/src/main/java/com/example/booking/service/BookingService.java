package com.example.booking.service;

import java.util.List;


import com.example.booking.model.Booking;

public interface BookingService {
	//add a new booking to the service
	void addBooking(Booking Booking);
	
	//return true if there is a booking with that ID
	default boolean hasBooking(String bookingid)
	{
		return getBooking(bookingid) !=null;
	}
	//returns all appointments template that can be edited in service implementation to add more flair
	default Booking getBooking(String bookingid) 
	{
		return getBooking(bookingid,null);
		
	}
	boolean BookingExists(String Datetime);
	//returns the appointments within the booking system
	
	Booking getBooking(String bookingid,String professor);
	//returns a list of all of the welcomes within the database
	List<Booking> getAllBookings();
	//Removes the booking with that booking id from the database
	void removeBooking(String Booking);
	
	// New method for searching bookings by professor
	public List<Booking> searchBookingsByProfessor(String professorName, String professorProgram);
	}


	

