package com.example.booking;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.booking.model.Booking;
import com.example.booking.service.BookingService;

@SpringBootApplication
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
		
	}
	
    // The initDB() @Bean is run automatically on startup, before the
    // @RestController is started.
    // The body could access command line argument args (but doesn't).
    // The fact that initDB() requires a WelcomeService argument tells Spring
    // to auto-configure and pass a WelcomeService instance, which initDB()
    // amends by adding more Welcome instances.
    @Bean
    public CommandLineRunner initDB(BookingService bs) {
        return (args) -> {
            bs.addBooking(new Booking("B001", "Interview Prep", "Professor A", "2023-12-10 10:00", "John Doe", "12345", "Computer Science", "2"));
            bs.addBooking(new Booking("B002", "CV advice", "Professor B", "2023-12-02 14:30", "Jane Doe", "54321", "Software Engineering", "3"));
            bs.addBooking(new Booking("B003", "Study advice", "Professor C", "2023-12-10 09:45", "Bob Smith", "67890", "Computer Science", "1"));
            bs.addBooking(new Booking("B004", "Exam Prep", "Professor D", "2023-12-04 16:15", "Alice Johnson", "98765", "Software Engineering", "4"));
            bs.addBooking(new Booking("B005", "Time Management", "Professor E", "2023-12-07 12:00", "Eva Brown", "23456", "Computer Science", "2"));
        };
    }

}
