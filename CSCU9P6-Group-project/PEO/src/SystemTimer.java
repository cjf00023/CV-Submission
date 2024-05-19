package PEO.src;

import java.util.Observable;

/**
 * The System Timer records the current date and time. 
 * The graphical user interface of this class has been separated into a new class.
 * This class provides the methods that the screen uses to manipulate the now DateAndTime.
 * It is observed by the TimeHandler.
 * There should be only one instance of this class.
 */
@SuppressWarnings("deprecation")
public class SystemTimer extends Observable{

	
	/**
	 * The current date and time.
	 */
	private DateAndTime now = new DateAndTime();
	
    public DateAndTime getNow() {
        return now;
    }
    
    /**
     * setNow takes a day and a time, rather than a date and time object
     * Not taking DateAndTime means the class always changes "now" by value, instead of to an external reference.
     */
    public void setNow(int day, int time) {
        this.now = new DateAndTime(day, time);
        setChanged();
        notifyObservers();
    }
    
    public void advanceDay(int day) {
        this.now.setDay(now.getDay() + day);
        setChanged();
        notifyObservers();
    }
    
    public void advanceTime(int time) {
        this.now.setTime(now.getTime() + time);
        setChanged();
        notifyObservers();
    }

}