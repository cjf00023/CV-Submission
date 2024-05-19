package PEO.src;

/**
 * A notification sent from the PEO system to a pupil or teacher. 
 * Recipients can view their notifications when they access the system. 
 */
public class Notification {

	//Other than read, these variables can only be set at construction. 
	private Person recipient;
	private DateAndTime timeSent;
	private boolean read;
	private String messageBody;

	public Notification(Person recipient, DateAndTime timeSent, String messageBody) {
		super();
		this.recipient = recipient;
		//DateAndTime is not a literal class, so this stucture is needed to pass the values instead of just a reference.
		this.timeSent = new DateAndTime(timeSent.getDay(),timeSent.getTime()); 
		read = false;
		this.messageBody = messageBody;
	}

	public Person getRecipient() {
		return recipient;
	}
	
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public DateAndTime getTimeSent() {
		return timeSent;
	}

	public String getMessageBody() {
		return messageBody;
	}
	
	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return recipient + ", "+timeSent.toString()+ ": " + messageBody;
	}

}