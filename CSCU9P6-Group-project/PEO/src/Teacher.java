package PEO.src;

public class Teacher extends Person {
	/**
	 * The subject taught by this teacher.
	 */
	private String subject;
	/**
	 * When a teacher is added to the system, a meeting room is created for them.
	 */
	private MeetingRoom myMeetingRoom;
	
	public Teacher(String name, String subject)
	{
		super(name);
		this.subject = subject;
		myMeetingRoom = new MeetingRoom(this);
	}
	
	public MeetingRoom getMyMeetingRoom()
	{
		return myMeetingRoom;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return "Teacher: "+ getName() + ", Subject: " + subject;
	}
}
