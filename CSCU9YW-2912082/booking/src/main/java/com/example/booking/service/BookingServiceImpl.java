package com.example.booking.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.booking.model.Booking;
@Component
@CrossOrigin
public class BookingServiceImpl implements BookingService{

	// Very simple in-memory database; key is the lang field of class Welcome.
    // We have to be careful with this 'database'. In order to avoid objects
    // in the database being mutated accidentally, we must always copy objects
    // before insertion and retrieval.
    private Map<String, Booking> db;
   

    public BookingServiceImpl() 
    {
    	db = new HashMap<>();
    }
	@Override
	  public void addBooking(Booking booking) {
        if (booking != null && booking.getBookingId() != null) {
            String slot = booking.getDatetime();

            // Check if a booking already exists for the specified day
            if (!BookingExists(slot)) {
                // Add booking to the database
                db.put(booking.getBookingId(), booking);
                System.out.println("Booking added successfully.");
            } else {
                // Send a response to the client saying that booking is not allowed on that day
                System.out.println("Booking already exists for the specified day. Please choose another day.");
            }
        }
    }

	  @Override
	    public boolean BookingExists(String datetime) {
	        // Check if a booking already exists for the specified day
	        for (Booking existingBooking : db.values()) {
	            if (existingBooking.getDatetime().equals(datetime)) {
	                return true;
	            }
	        }
	        return false;
	    }

	// Returns a booking with the students details.
    // 
	@Override
	public Booking getBooking(String bookingid, String student) {
		Booking booking = db.get(bookingid);
        if (booking == null) {
            return null;
        }
        // copying welcome to protect objects in the database from changes
        booking = new Booking(booking);
        if (bookingid != null) {
            booking.setBookingId(booking.getBookingId() + ", " + bookingid);
        }
        return booking;
	}
	//Methd from the demo that can be used so that can view all of the bookings that he has for that day.
	@Override
	public List<Booking> getAllBookings() {
		return new ArrayList<>(db.values());
	}

	@Override
	public void removeBooking(String Booking) {
		if(Booking != null)
		{
			db.remove(Booking);
		}
		
	}
	// New method for searching bookings by professor
    @Override
    public List<Booking> searchBookingsByProfessor(String professorName, String professorProgram) {
        List<Booking> matchingBookings = new ArrayList<>();

        for (Booking booking : db.values()) {
            if (booking.getProfessor().equals(professorName) && booking.getStudent().getProgram().equals(professorProgram) ) {
                matchingBookings.add(booking);
            }
        }

        return matchingBookings;
    }

}
