package com.example.booking.controller;
import org.springframework.http.HttpHeaders;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.booking.model.Booking;
import com.example.booking.service.BookingService;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class BookingController {
	 private final String ROOT_PATH = "/ding";
	 //import the booking service into the system
	private BookingService bs;
	public BookingController(BookingService bs)
	{
		this.bs = bs;
	}
	@GetMapping(ROOT_PATH)
	public List<Booking> getAllBookings()
	{
		return bs.getAllBookings();
	}
	
	@PostMapping(ROOT_PATH)
	public ResponseEntity<Void> addBooking(@RequestBody Booking newBooking) {
	    // Generate a random UUID as the booking ID
	    String generatedBookingId = UUID.randomUUID().toString();

	    // Set the generated ID in the newBooking object
	    newBooking.setBookingId(generatedBookingId);

	    // Check if booking already exists for the specified day
	    if (bs.BookingExists(newBooking.getDatetime())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking already exists for the specified day. Please choose another day.");
	    }

	    // Add the new booking
	    bs.addBooking(newBooking);

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Location", ROOT_PATH + "/" + newBooking.getBookingId());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}


			
	        @GetMapping(ROOT_PATH + "/{bookingId}")
	        public Booking getBooking(@PathVariable String bookingId, @RequestParam(required=false) String student) {
	            Booking booking = bs.getBooking(bookingId, student);
	            if (booking == null) {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	            }
	            return booking;
	        }
	        @PutMapping(ROOT_PATH + "/{bookingId}")
	        public ResponseEntity<Void> updateBooking(@RequestBody Booking newbooking, @PathVariable String bookingId) {
	            if (!bs.hasBooking(bookingId)){
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	            }
	            if (!Objects.equals(bookingId, newbooking.getBookingId())) {
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	            }
	            bs.addBooking(newbooking);
	            return new ResponseEntity<>(HttpStatus.ACCEPTED);
	        }
	        @DeleteMapping(ROOT_PATH + "/{bookingid}")
	        public ResponseEntity<Void> removeBooking(@PathVariable String bookingid) {
	            if (!bs.hasBooking(bookingid)) {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	            }
	            bs.removeBooking(bookingid);
	            return new ResponseEntity<>(HttpStatus.OK);
	        }
	     // Your logic to search bookings by professor in the professor client
	        @GetMapping(ROOT_PATH + "/search")
	        public ResponseEntity<List<Booking>> searchBookingsByProfessor(
	                @RequestParam(name = "professorName") String professorName,
	                @RequestParam(name = "professorProgram") String professorProgram) {
	            
	            List<Booking> bookings = bs.searchBookingsByProfessor(professorName, professorProgram);
	            return new ResponseEntity<>(bookings, HttpStatus.OK);
	        }


}
	        
	        

