
package PEO.src;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is observable, and it helps update the ParentScreens and
 * TeacherScreens. A teacher will observe only their own meeting room. Parents
 * can observe different teachers' meeting rooms as they join and leave meetings
 * with different teachers. A parent can only join or be in the lobby of one
 * teacher's meeting room at any point in time.
 */
@SuppressWarnings("deprecation")
public class MeetingRoom extends Observable {

	private Teacher owner;
	private ArrayList<Timeslot> schedule;
	private boolean isOpen;
	private int currentMeetingTimeslot; // This variable is set to -1 when the parentsEvening is not on.
	private boolean parentPresent;
	private ArrayList<Pupil> parentsWaitingInLobby;

	public Teacher getOwner() {
		return owner;
	}

	public ArrayList<Timeslot> getSchedule() {
		return schedule;
	}

	/**
	 * Sets up the Schedule of a Meetingroom with the declared number and length of
	 * timeslots
	 * 
	 * @param startTime     The start point of the first timeslot, ie. Parents
	 *                      Evening Start Time
	 * @param meetingNumber Number of timeslots to create. No timeslots are created
	 *                      for negative values.
	 * @param meetingLength Length of time between timeslots in minutes.
	 */
	public void setSchedule(DateAndTime startTime, int meetingNumber, int meetingLength) {
		ArrayList<Timeslot> schedule = new ArrayList<>();

		for (int i = 0; i < meetingNumber; i++) {
			DateAndTime slotTime = new DateAndTime(startTime.getDay(), startTime.getTime() + i * meetingLength); // Every
																													// timeslot
																													// starts
																													// one
																													// meetingLength
																													// after
																													// the
																													// previous.
			Timeslot t = new Timeslot(slotTime);
			t.setMeetingLogEntry(getOwner().getName() + " has no meeting scheduled at " + t.getStartTime());
			schedule.add(t);
		}
		this.schedule = schedule;
		setChanged();
		notifyObservers();
	}

	public Timeslot getTimeslot(int slotNumber) {
		return schedule.get(slotNumber);
	}

	public void setTeacherAvailability(int slotNumber, boolean availability) {
		schedule.get(slotNumber).setTeacherAvailable(availability);
		setChanged();
		notifyObservers();
	}

	public boolean isOpen() {
		return isOpen;
	}

	public int getCurrentMeetingTimeslot() {
		return currentMeetingTimeslot;
	}

	public Pupil getCurrentMeetingBookedFor() {
		return schedule.get(currentMeetingTimeslot).getBookedFor();
	}

	public boolean getParentPresent() {
		return parentPresent;
	}

	public ArrayList<Pupil> getParentsWaitingInLobby() {
		return parentsWaitingInLobby;
	}

	public MeetingRoom(Teacher o) {
		owner = o;
		schedule = new ArrayList<>();
		isOpen = false;
		parentPresent = false;
		parentsWaitingInLobby = new ArrayList<>();
		currentMeetingTimeslot = -1;
	}

	/*
	 * This method is called at the start of the parents' evening only
	 */
	public void open() {
		isOpen = true;
		currentMeetingTimeslot = 0;
		setChanged();
		notifyObservers();
	}

	/*
	 * This method is called at the end of the parents' evening only
	 */
	public void close() {
		if (parentPresent) {
			removeParent();
		}
		isOpen = false;
		currentMeetingTimeslot = -1;
		parentsWaitingInLobby.clear();
		setChanged();
		notifyObservers();
	}

	/**
	 * This operation makes an appointment for a parent to meet a teacher during a
	 * time slot when that teacher is available.
	 */
	public boolean makeAppointment(Pupil p, int t) {
		Timeslot slot = schedule.get(t);
		if (slot.isTeacherAvailable() && slot.getBookedFor() == null) {
			slot.setBookedFor(p);
			setTeacherAvailability(t, false);
			slot.setMeetingLogEntry(getOwner().getName() + " is scheduled to meet with " + slot.getBookedFor().getName()
					+ " at " + slot.getStartTime());
			setChanged();
			notifyObservers();
			return true;
		} else if (slot.getBookedFor() == p) {
			return true;
		}
		return false;
	}

	public boolean cancelAppointment(Pupil p, int t) {
		Timeslot slot = schedule.get(t);
		if (slot.getBookedFor() == p) {
			slot.setBookedFor(null);
			setTeacherAvailability(t, true);
			slot.setMeetingLogEntry(getOwner().getName() + " has no meeting scheduled at " + slot.getStartTime());
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	/**
	 * Developers Note: Don't let people without appointments into the lobby 
	 * 
	 * A parent attempts to join the meeting room.
	 * 
	 * If the meeting room is open and the parent has an appointment at the current
	 * time, then the parent is allowed in to meet the teacher.
	 * 
	 * If the meeting room is closed {Edit: Changed from or} and the parent's
	 * appointment in the future, the parent is placed in the lobby.
	 * 
	 * If the parent does not have an appointment or their appointment was in the
	 * past, they are refused entry to the meeting room.
	 */
	public void join(Pupil p) {
		if (isOpen && getCurrentMeetingBookedFor() == p) {
			parentPresent = true;
			setChanged();
			notifyObservers();
		} else if (hasFutureMeeting(p)) {
			parentsWaitingInLobby.add(p);
			setChanged();
			notifyObservers();
		} else {
			// Refuse Entry
		}
	}

	public void leave(Pupil p) {
		if (getCurrentMeetingBookedFor() == p) {
			parentPresent = false;
		}
		parentsWaitingInLobby.remove(p);
		setChanged();
		notifyObservers();
	}

	/**
	 * This operation is triggered when it is time to end a meeting and start the
	 * next one. Any parent currently in the meeting room is removed. If there is a
	 * parent in the lobby who has a meeting booked at the current time, they are
	 * moved from the lobby into the meeting with the teacher.
	 */
	public void allChange() {
		removeParent();
		currentMeetingTimeslot++;
		parentPresent = parentsWaitingInLobby.contains(getCurrentMeetingBookedFor());
		if (parentPresent) {
			parentsWaitingInLobby.remove(getCurrentMeetingBookedFor());
		}
		setChanged();
		notifyObservers();

	}

	private boolean hasFutureMeeting(Pupil p) {
		if (currentMeetingTimeslot<0) {
			return false;
		}
		for (int i = currentMeetingTimeslot; i < schedule.size(); i++) {
			if (schedule.get(i).getBookedFor() == p)
				return true;
		}
		return false;
	}

	/**
	 * Removes the parent currently present in the MeetingRoom
	 */
	private void removeParent() {
		Timeslot slot = getTimeslot(currentMeetingTimeslot);
		if (slot.getBookedFor() == null) {
			slot.setMeetingLogEntry(getOwner().getName() + " had no meeting at " + slot.getStartTime());
		} else {
			slot.setMeetingLogEntry(
					getOwner().getName() + " met with " + slot.getBookedFor().getName() + " at " + slot.getStartTime());
		}
		parentPresent = false;
	}

}