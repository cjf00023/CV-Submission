package PEO.src;

/**
 * This class is a basic representation of date and time. The attributes can be
 * represented simply as integers.
 */
public class DateAndTime implements Comparable<DateAndTime> {

	private int day;
	private int time;

	public DateAndTime(int day, int time) {
		this.day = day;
		this.time = time;
	}

	//Default constructor was retained to support older code that relied on it. We prefer using only the full constructor.
	public DateAndTime() {
		day = 0;
		time = 0;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTime() {
		return time;
	}

	//Time should never be set to negative values.
	public void setTime(int time) {
		if (time >= 0) {
			this.time = time;
		}
	}

	/*
	 * Negative returns mean other is lesser, ie. earlier than this
	 * Positive returns mean other is greater, ie. later than this 
	 * Zero as the return means that other is equal, ie. the same time as this
	 */
	@Override
	public int compareTo(DateAndTime other) {
		if (Integer.compare(day, other.getDay()) != 0) {
			return Integer.compare(day, other.getDay());
		} else {
			return Integer.compare(time, other.getTime());
		}
	}

	// Two date and time objects are equal if they represent the same point in time
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof DateAndTime)) {
			return false;
		}
		DateAndTime other = (DateAndTime) o;
		return this.compareTo(other)==0;
	}

	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return "Day: " + day + ",Time: " + time;
	}
}