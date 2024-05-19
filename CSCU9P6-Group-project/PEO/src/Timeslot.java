package PEO.src;

/**
 * A time slot during the parent's evening when a meeting may take place.
 */
public class Timeslot {

	private DateAndTime startTime;
	private boolean teacherAvailable;
	private Pupil bookedFor;
	/**
	 * The system will create a log entry after the meeting ends or the appointment
	 * time is over, indicating whether or not the parent attended the meeting.
	 */
	private String meetingLogEntry;
	
	public Timeslot(DateAndTime startTime) {
		super();
		this.startTime = startTime;
		teacherAvailable = true;
		
	}

	public DateAndTime getStartTime() {
		return startTime;
	}

	public boolean isTeacherAvailable() {
		return teacherAvailable;
	}

	public void setTeacherAvailable(boolean teacherAvailable) {
		this.teacherAvailable = teacherAvailable;
	}

	public Pupil getBookedFor() {
		return bookedFor;
	}

	public void setBookedFor(Pupil bookedFor) {
		this.bookedFor = bookedFor;
	}

	public String getMeetingLogEntry() {
		return meetingLogEntry;
	}

	public void setMeetingLogEntry(String meetingLogEntry) {
		this.meetingLogEntry = meetingLogEntry;
	}
	
	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return "Timeslot at " + startTime + " with " + bookedFor + "./nLog: " + meetingLogEntry;
	}
}