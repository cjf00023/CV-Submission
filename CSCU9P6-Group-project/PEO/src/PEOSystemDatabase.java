package PEO.src;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This class holds the core data and provides the core functionality of the PEO system. 
 */
@SuppressWarnings("deprecation")
public class PEOSystemDatabase extends Observable {

	/**
	 * Represents the current status of the PEO system, which may be Dormant,
	 * PreparingForBooking, BookingAppointments, BookingsEnded, or
	 * ParentsEveningOn.
	 */
	private int systemStatus;
	private String systemStatusMessage;
	
	private static int Dormant = 0;
	private static int PreparingForBooking = 1;
	private static int BookingAppointments = 2;
	private static int BookingsEnded = 3;
	private static int ParentsEveningOn = 4;

	/**
	 * This block represents the times when the system will transition from one state to another. 
	 * These times are required to be in this chronological order for correct functioning of the system.
	 * All of them have to take place after the current time as well.
	 */
	private DateAndTime bookingsOpenTime;
	private DateAndTime bookingsCloseTime;
	private DateAndTime parentsEveningStartTime;
	private DateAndTime parentsEveningEndTime;
	
	private DateAndTime currentTime;
	
	/**
	 * This is the index of the current time slot in the schedules of the
	 * teachers' meeting rooms. It is set to 0 when the parents evening begins and
	 * is updated whenever the TimeHandler is notified that time has passed during
	 * the parents evening. It is used to trigger the start and end of meetings.
	 */
	private int currentMeetingTimeslot;
	
	/**
	 * The duration in minutes of a meeting between a teacher and a parent/guardian.
	 * This is used to calculate the number of time slots available for meetings during the parents evening, 
	 * and the start time of each time slot.
	 */
	private int meetingLength;
	
	private ArrayList<Teacher> staffList;
	private ArrayList<Pupil> schoolRoll;
	
	/**
	 * The record of all notification sent by the system.
	 */
	private ArrayList<Notification> notifications;

	public PEOSystemDatabase() {
		systemStatus = Dormant;
		systemStatusMessage = "System is Dormant";
		bookingsOpenTime = new DateAndTime();
		bookingsCloseTime = new DateAndTime();
		parentsEveningStartTime = new DateAndTime();
		parentsEveningEndTime = new DateAndTime();
		currentTime = new DateAndTime();
		currentMeetingTimeslot = -1;
		meetingLength = -1;
		staffList = new ArrayList<Teacher>();
		schoolRoll = new ArrayList<Pupil>();
		notifications = new ArrayList<Notification>();
	}

	public int getSystemStatus() {
		return systemStatus;
	}

	public String getSystemStatusMessage() {
		return systemStatusMessage;
	}

	public String setSystemStatusMessage(String systemStatusMessage) {
		this.systemStatusMessage = systemStatusMessage;
		setChanged();
	    notifyObservers();
		return systemStatusMessage;
	}

	public DateAndTime getBookingsOpenTime() {
		return bookingsOpenTime;
	}

	public boolean setBookingsOpenTime(DateAndTime bookingsOpenTime) {
		if (systemStatus != Dormant) {
			return false;
		}
		this.bookingsOpenTime = bookingsOpenTime;
		return true;
	}

	public DateAndTime getBookingsCloseTime() {
		return bookingsCloseTime;
	}

	public boolean setBookingsCloseTime(DateAndTime bookingsCloseTime) {
		if (systemStatus != Dormant) {
			return false;
		}
		this.bookingsCloseTime = bookingsCloseTime;
		return true;
	}

	public DateAndTime getParentsEveningStartTime() {
		return parentsEveningStartTime;
	}

	public boolean setParentsEveningStartTime(DateAndTime parentsEveningStartTime) {
		if (systemStatus != Dormant) {
			return false;
		}
		this.parentsEveningStartTime = parentsEveningStartTime;
		return true;
	}

	public DateAndTime getParentsEveningEndTime() {
		return parentsEveningEndTime;
	}

	public boolean setParentsEveningEndTime(DateAndTime parentsEveningEndTime) {
		if (systemStatus != Dormant) {
			return false;
		}
		this.parentsEveningEndTime = parentsEveningEndTime;
		return true;
	}

	// Having both seperated and combined setters for the significant times is useful, as it allows for automatic checking of the validity of dates.
	public boolean setTimes(DateAndTime bookingsOpenTime, DateAndTime bookingsCloseTime, DateAndTime parentsEveningStartTime,DateAndTime parentsEveningEndTime) {
		if (!areTimesValid(bookingsOpenTime, bookingsCloseTime, parentsEveningStartTime, parentsEveningEndTime)) {
			return false;
		}
		setBookingsOpenTime(bookingsOpenTime);
		setBookingsCloseTime(bookingsCloseTime);
		setParentsEveningStartTime(parentsEveningStartTime);
		setParentsEveningEndTime(parentsEveningEndTime);
		return true;
		
	}

	public int getMeetingLength() {
		return meetingLength;
	}

	public boolean setMeetingLength(int meetingLength) {
		if (systemStatus != Dormant) {
			return false;
		}
		if (meetingLength < 0) {
			return false;
		}
		this.meetingLength = meetingLength;
		return true;
	}

	public DateAndTime getCurrentTime() {
		return currentTime;
	}

	public int getCurrentMeetingTimeslot() {
		return currentMeetingTimeslot;
	}

	public void setCurrentMeetingTimeslot(int currentMeetingTimeslot) {
		this.currentMeetingTimeslot = currentMeetingTimeslot;
	}

	public void incrementCurrentMeetingTimeslot() {
		this.currentMeetingTimeslot += 1;
	}

	public ArrayList<Teacher> getStaffList() {
		return staffList;
	}

	public ArrayList<String[]> getStaffListAsString() {
		ArrayList<String[]> output = new ArrayList<String[]>();
		for (Teacher teacher : staffList) {
			String[] teacherObject = new String[2];
			teacherObject[0] = teacher.getName();
			teacherObject[1] = teacher.getSubject();
			output.add(teacherObject);
		}
		return output;
	}

	public ArrayList<Pupil> getSchoolRoll() {
		return schoolRoll;
	}

	public ArrayList<String[]> getSchoolRollAsString() {
		ArrayList<String[]> output = new ArrayList<String[]>();
		for (Pupil pupil : schoolRoll) {
			String[] pupilObject = new String[2];
			pupilObject[0] = pupil.getName();
			pupilObject[1] = pupil.getContactParent().getName();
			output.add(pupilObject);
		}
		return output;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	/**
	 * The administrator uses this operation to clear old notifications when the
	 * system is going to be set up for a new parents evening.
	 */
	public void clearNotifications() {
		notifications.clear();
	}

	public Pupil getPupil(String name) {
		int pupilLocation = schoolRoll.indexOf(new Person(name));
		if (pupilLocation != -1) {
			return schoolRoll.get(pupilLocation);
		} else {
			return null;
		}
	}

	/**
	 * The administrator uses this operation to add the details of a pupil to the
	 * system. You will need to add other operations to remove and edit pupils.
	 */
	public boolean addPupil(String pupilName, String contactPerson) {
		if (systemStatus != Dormant) {
			return false;
		}
		if (!(pupilName != null && !pupilName.isBlank()) || !(contactPerson != null && !contactPerson.isBlank())) {
			return false;
		}
		Pupil newPupil = new Pupil(pupilName);
		newPupil.setContactParent(contactPerson);
		if (newPupil.getName().equals(pupilName) && newPupil.getContactParent().getName().equals(contactPerson)
				&& !(schoolRoll.contains(new Person(pupilName)))) {
			schoolRoll.add(newPupil);
			return true;
		} else {
			return false;
		}
	}

	public boolean editPupil(String oldName, String newName, String contactPerson, String teacherToAddName,
			String teacherToRemoveName) {
		if (systemStatus != Dormant) {
			return false;
		}
		Pupil pupil = getPupil(oldName);
		if (pupil == null) {
			return false;
		}
		boolean editSuccessful = true;
		if (newName != null && !newName.isBlank() && !(schoolRoll.contains(new Person(newName)))) {
			pupil.setName(newName);
			editSuccessful = editSuccessful && pupil.getName().equals(newName);
		}
		if (contactPerson != null && !contactPerson.isBlank()) {
			pupil.setContactParent(contactPerson);
			editSuccessful = editSuccessful && pupil.getContactParent().getName().equals(contactPerson);
		}
		if (teacherToAddName != null && !teacherToAddName.isBlank()) {
			Teacher teacherToAdd = getTeacher(teacherToAddName);
			if (teacherToAdd == null) {
				return false;
			}
			if (!(pupil.getTaughtBy().contains(teacherToAdd))) {
				pupil.addTeacher(teacherToAdd);
				editSuccessful = editSuccessful && pupil.getTaughtBy().contains(teacherToAdd);
			} else {
				editSuccessful = false;
			}
		}
		if (teacherToRemoveName != null && !teacherToRemoveName.isBlank()) {
			Teacher teacherToRemove = getTeacher(teacherToRemoveName);
			if (teacherToRemove == null) {
				return false;
			}
			if (pupil.getTaughtBy().contains(teacherToRemove)) {
				pupil.removeTeacher(teacherToRemove);
				editSuccessful = editSuccessful && !(pupil.getTaughtBy().contains(teacherToRemove));
			} else {
				editSuccessful = false;
			}
		}
		return editSuccessful;
	}

	public boolean removePupil(String pupilToRemoveName) {
		if (systemStatus != Dormant) {
			return false;
		}
		Pupil pupilToRemove = getPupil(pupilToRemoveName);
		if (pupilToRemove == null) {
			return false;
		}
		// If the pupil can be found by getPupil, it exists within schoolRoll.
		schoolRoll.remove(pupilToRemove);
		return true;
	}

	public Teacher getTeacher(String name) {
		int teacherLocation = staffList.indexOf(new Person(name));
		if (teacherLocation != -1) {
			return staffList.get(teacherLocation);
		} else {
			return null;
		}
	}

	/**
	 * The administrator uses this operation to add a teacher to the system. There
	 * will be other operations to remove and edit teachers.
	 */
	public boolean addTeacher(String teacherName, String subject) {
		if (systemStatus != Dormant) {
			return false;
		}
		if (!(teacherName != null && !teacherName.isBlank()) || !(subject != null && !subject.isBlank())) {
			return false;
		}
		Teacher newTeacher = new Teacher(teacherName, subject);
		if (newTeacher.getName().equals(teacherName) && newTeacher.getSubject().equals(subject)
				&& !(staffList.contains(new Person(teacherName)))) {
			staffList.add(newTeacher);
			return true;
		} else {
			return false;
		}
	}

	public boolean editTeacher(String oldName, String newName, String subject) {
		if (systemStatus != Dormant) {
			return false;
		}
		Teacher teacher = getTeacher(oldName);
		if (teacher == null) {
			return false;
		}
		boolean editSuccessful = true;
		if (newName != null && !newName.isBlank() && !(staffList.contains(new Person(newName)))) {
			teacher.setName(newName);
			editSuccessful = editSuccessful && teacher.getName().equals(newName);
		}
		if (subject != null && !subject.isBlank()) {
			teacher.setSubject(subject);
			editSuccessful = editSuccessful && teacher.getSubject().equals(subject);
		}
		return editSuccessful;
	}

	public boolean removeTeacher(String teacherToRemoveName) {
		if (systemStatus != Dormant) {
			return false;
		}
		Teacher teacherToRemove = getTeacher(teacherToRemoveName);
		if (teacherToRemove == null) {
			return false;
		}
		// If the teacher can be found by getTeacher, it exists within staffList.
		staffList.remove(teacherToRemove);
		for (Pupil p : schoolRoll) {
			p.removeTeacher(teacherToRemove);
		}
		return true;
	}

	/**
	 * Create and send a notification to a parent or a teacher.
	 */

	public boolean sendNotification(String recipient, String MessageBody, boolean isTeacher) {
		Person recipientObj;
		if (isTeacher) {
			recipientObj = getTeacher(recipient);
		} else {
			recipientObj = getPupil(recipient);
		}
		if (recipientObj == null) {
			return false;
		}
		Notification notif = new Notification(recipientObj,
				new DateAndTime(currentTime.getDay(), currentTime.getTime()), MessageBody);
		if (notif.getRecipient().equals(recipientObj) && notif.getTimeSent().equals(currentTime)
				&& notif.getMessageBody().equals(MessageBody) && (recipientObj instanceof Teacher == isTeacher)) {
			notifications.add(notif);
			setChanged();
			notifyObservers("Notification");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The administrator uses this operation to see the log of meetings that have
	 * taken place. This could represent a group of operations to allow the log to
	 * be viewed in different way.
	 */
	public ArrayList<String> viewMeetingLog() {
		ArrayList<String> meetingLogs = new ArrayList<String>();
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			for (Timeslot timeslot : meetingRoom.getSchedule()) {
				meetingLogs.add(timeslot.getMeetingLogEntry());
			}
		}
		return meetingLogs;
	}

	public ArrayList<String> viewMeetingLogsByTeacher(String teacherName) {
		ArrayList<String> meetingLogs = new ArrayList<String>();
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return meetingLogs;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		for (Timeslot timeslot : meetingRoom.getSchedule()) {
			meetingLogs.add(timeslot.getMeetingLogEntry());
		}
		return meetingLogs;
	}

	public ArrayList<String> viewMeetingLogsByPupil(String pupilName) {
		ArrayList<String> meetingLogs = new ArrayList<String>();
		Pupil pupil = getPupil(pupilName);
		if (pupil == null) {
			return meetingLogs;
		}
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			for (Timeslot timeslot : meetingRoom.getSchedule()) {
				Object bookedFor = timeslot.getBookedFor();
				if (bookedFor instanceof Pupil && ((Pupil)bookedFor).getName().equals(pupilName)) {
					meetingLogs.add(timeslot.getMeetingLogEntry());
				}
			}
		}
		return meetingLogs;
	}

	public ArrayList<String> viewMeetingLogsInTimeslot(int timeslot) {
		ArrayList<String> meetingLogs = new ArrayList<String>();
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
			if (timeslot >= 0 && timeslot < schedule.size()) {
				meetingLogs.add(schedule.get(timeslot).getMeetingLogEntry());
			}
		}
		return meetingLogs;
	}
	
	public void clearMeetingLogs() {
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			for (Timeslot timeslot : meetingRoom.getSchedule()) {
				timeslot.setMeetingLogEntry("");
			}
		}
	}

	/**
	 * Parents and teachers can use this operation to read notifications sent to
	 * them by the system.
	 */
	public ArrayList<Notification> viewNotifications(String recipient, boolean isTeacher) {
		ArrayList<Notification> viewableNotifications = new ArrayList<Notification>();
		for (Notification notif : notifications) {
			if (notif.getRecipient().equals(new Person(recipient))
					&& notif.getRecipient() instanceof Teacher == isTeacher) {
				viewableNotifications.add(notif);
				notif.setRead(true);
			}
		}
		return viewableNotifications;
	}

	/**
	 * This method requires the user to specify a time slot, and attempts to book it for them.
	 */
	public boolean bookAppointmentManually(String pupilName, String teacherName, int timeslot) {
		if (systemStatus != BookingAppointments) {
			return false;
		}
		Pupil pupil = getPupil(pupilName);
		if (pupil == null) {
			return false;
		}
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return false;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		int size = meetingRoom.getSchedule().size();
		if (timeslot < 0 || timeslot >= size) {
			return false;
		}
		for (int x = 0; x < size; x++) {
			if (meetingRoom.getTimeslot(x).getBookedFor() == pupil && x != timeslot) {
				meetingRoom.cancelAppointment(pupil, x);
			}
		}
		boolean successful = meetingRoom.makeAppointment(pupil, timeslot);
		if (successful) {
			sendNotification(pupilName, "Appointment booked with " + teacherName, false);
		}
		return successful;
	}

	public boolean cancelAppointmentManually(String pupilName, String teacherName, int timeslot) {
		if (systemStatus != BookingAppointments) {
			return false;
		}
		Pupil pupil = getPupil(pupilName);
		if (pupil == null) {
			return false;
		}
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return false;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		int size = meetingRoom.getSchedule().size();
		if (timeslot <= 0 || timeslot >= size) {
			return false;
		}
		boolean successful = meetingRoom.cancelAppointment(pupil, timeslot);
		sendNotification(pupil.getName(), "notification of appointment cancellation", false);
		return successful;
	}

	/**
	 * Returns the first free timeslot available for a teacher
	 */
	public int getFirstAvailableTimeslot(String teacherName) {
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return -1;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
		for (int timeslot = 0; timeslot < schedule.size(); timeslot++) {
			if (schedule.get(timeslot).isTeacherAvailable()) {
				return timeslot;
			}
		}
		return -1;
	}

	/**
	 * Returns the first free timeslot available for a teacher between the start and
	 * end points (inclusive) (used to allow some control over the automatic
	 * booking, while keeping the benefit of the automatic assignment)
	 */
	public int getFirstAvailableTimeslotBetweenTwoPoints(String teacherName, int startPoint, int endPoint) {
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return -1;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
		if (startPoint < 0) {
			startPoint = 0;
		}
		if (endPoint >= schedule.size()) {
			endPoint = schedule.size() - 1;
		}
		for (int timeslot = startPoint; timeslot <= endPoint; timeslot++) {
			if (schedule.get(timeslot).isTeacherAvailable()) {
				return timeslot;
			}
		}
		return -1;
	}

	/**
	 * Return the first free timeslot available for a teacher, unless it is present
	 * in the excluded list (can be used to avoid conflicts)
	 */

	public int getFirstAvailableTimeslotExcludingTimeslots(String teacherName, ArrayList<Integer> excluded) {
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return -1;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
		for (int timeslot = 0; timeslot < schedule.size(); timeslot++) {
			if (schedule.get(timeslot).isTeacherAvailable() && !(excluded.contains(timeslot))) {
				return timeslot;
			}
		}
		return -1;
	}

	public ArrayList<Integer> getAllAvailableTimeslots(String teacherName) {
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return null;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
		ArrayList<Integer> available = new ArrayList<Integer>();
		for (int timeslot = 0; timeslot < schedule.size(); timeslot++) {
			if (meetingRoom.getTimeslot(timeslot).isTeacherAvailable()) {
				available.add(timeslot);
			}
		}
		return available;
	}

	public ArrayList<Timeslot> getAllTimeslots(String teacherName) {
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return null;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		ArrayList<Timeslot> schedule = meetingRoom.getSchedule();
		ArrayList<Timeslot> available = new ArrayList<Timeslot>();
		for (int timeslot = 0; timeslot < schedule.size(); timeslot++) {
			if (meetingRoom.getTimeslot(timeslot).isTeacherAvailable()) {
				available.add(meetingRoom.getTimeslot(timeslot));
			}
		}
		return available;
	}

	
	public boolean setAvailability(String teacherName, int timeslot, boolean available) {
		if (systemStatus != PreparingForBooking) {
			return false;
		}
		Teacher teacher = getTeacher(teacherName);
		if (teacher == null) {
			return false;
		}
		MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
		Timeslot time = meetingRoom.getTimeslot(timeslot);
		time.setTeacherAvailable(available);
		return time.isTeacherAvailable() == available;
	}

	public boolean switchAvailability(String teacherName, int timeslot) {
		return setAvailability(teacherName, timeslot,
				!getTeacher(teacherName).getMyMeetingRoom().getTimeslot(timeslot).isTeacherAvailable());
	}

	/**
	 * Create empty timeslots in all meeting rooms, according to the set parents
	 * evening times and the meeting length.
	 *
	 * @return true if the operations succeeded, false if the length of the parents
	 *         evening did not divide evenly into the meeting length.
	 */
	public boolean setupMeetingRoomTimeslots() {
		if (systemStatus != Dormant) {
			return false;
		}
		int minutes = parentsEveningEndTime.getTime() - parentsEveningStartTime.getTime();
		if (((float) minutes) % meetingLength != 0) {
			return false;
		}

		int numberOfTimeslots = minutes / meetingLength;
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			meetingRoom.setSchedule(parentsEveningStartTime, numberOfTimeslots, meetingLength);
			if (meetingRoom.getSchedule().size() != numberOfTimeslots) {
				return false;
			}
		}
		return true;
	}

	public boolean prepareForBookings() {

		if (systemStatus != Dormant) {
			return false;
		}
		boolean success = setupMeetingRoomTimeslots();
		success = areTimesValid(null,null,null,null) && success;
		if (success) {
			systemStatus = PreparingForBooking;
			clearNotifications();
			for (Teacher t : staffList) {
				sendNotification(t.getName(),
						"System is now preparing for bookings, please set up your availability for meetings", true);
			}
			setChanged();
			notifyObservers();
		}
		return success;
	}

	/**
	 * Called by the TimeHandler when it detects that BookingsOpenTime has been
	 * reached. The system state is changed to bookingsOpen and notifications are
	 * sent to parents asking them to book appointments to meet their children's
	 * teachers. 
	 */
	public boolean openBookings() {
		if (systemStatus != PreparingForBooking) {
			return false;
		}
		for (Pupil pupil : schoolRoll) {
			sendNotification(pupil.getName(), "notification of bookings opening", false);
		}
		systemStatus = BookingAppointments;
		setChanged();
		notifyObservers();
		return true;
	}
	
	/**
	 * Called by the TimeHandler when it detects that BookingsCloseTime has been
	 * reached. The system state is changed to bookingsClosed.
	 */
	public boolean closeBookings() {
		if (systemStatus != BookingAppointments) {
			return false;
		}
		systemStatus = BookingsEnded;
		setChanged();
		notifyObservers();
		return true;
	}

	/**
	 * Called by the TimeHandler when it detects that ParentsEveningStartTime has been
	 * reached. The system state is changed to ParentsEveningOn, and all meetingRooms are opened.
	 */
	public boolean beginParentsEvening() {
		if (systemStatus != BookingsEnded) {
			return false;
		}
		setCurrentMeetingTimeslot(0);
		for (Teacher x : staffList) {
			MeetingRoom m = x.getMyMeetingRoom();
			m.open();
			if (m.getCurrentMeetingTimeslot() != 0) {
				return false;
			}
		}
		updateTime(currentTime);
		systemStatus = ParentsEveningOn;
		setChanged();
		notifyObservers();
		return true;
	}

	/**
	 * 
	 * Called by the TimeHandler when it detects that ParentsEveningEndTime has been
	 * reached. The system state is changed to Dormant
	 * Note that, as described in the system specification, all meeting rooms are
	 * closed when the system becomes dormant, but no data is erased automatically.
	 */
	public boolean returnToDormant() {
		if (systemStatus != ParentsEveningOn) {
			return false;
		}
		setCurrentMeetingTimeslot(-1);
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			meetingRoom.close();

			if (meetingRoom.getCurrentMeetingTimeslot() != -1) {
				return false;
			}
		}
		systemStatus = Dormant;
		setChanged();
		notifyObservers();
		return true;
	}

	public boolean updateTime(DateAndTime systemCurrentTime) {
		currentTime = systemCurrentTime;
		if (currentTime.compareTo(parentsEveningStartTime) >= 0 && parentsEveningEndTime.compareTo(currentTime) >= 0) {
			// Cast this as float to avoid unintended behaviour from integer division.
			int timeslot = (int) ((currentTime.getTime() - parentsEveningStartTime.getTime()) / meetingLength);
			if (timeslot < 0) {
				return false;
			}
			currentMeetingTimeslot = timeslot;
		}
		setChanged();
		notifyObservers();
		return true;
	}

	public boolean updateMeetingRooms() {
		if (systemStatus != ParentsEveningOn) {
			return true;
		}
		boolean successfulUpdate = true;
		for (Teacher teacher : staffList) {
			MeetingRoom meetingRoom = teacher.getMyMeetingRoom();
			int localTimeslot = meetingRoom.getCurrentMeetingTimeslot();
			for (int timeslot = localTimeslot; timeslot < currentMeetingTimeslot; timeslot++) {
				meetingRoom.allChange();
			}
			successfulUpdate = successfulUpdate && meetingRoom.getCurrentMeetingTimeslot() == currentMeetingTimeslot;
		}
		return successfulUpdate;
	}

	public boolean areTimesValid(DateAndTime bookingsOpen, DateAndTime bookingsClose, DateAndTime parentsStart,
			DateAndTime parentsEnd) {
		if (systemStatus != Dormant) {
			return false;
		}
		if (bookingsOpen == null) {
			bookingsOpen = bookingsOpenTime;
		}
		if (bookingsClose == null) {
			bookingsClose = bookingsCloseTime;
		}
		if (parentsStart == null) {
			parentsStart = parentsEveningStartTime;
		}
		if (parentsEnd == null) {
			parentsEnd = parentsEveningEndTime;
		}
		return (parentsEnd.compareTo(parentsStart) > 0) && (parentsEnd.getDay() == parentsStart.getDay())&& (parentsStart.compareTo(bookingsClose) > 0)
				&& (bookingsClose.compareTo(bookingsOpen) > 0) && (bookingsOpen.compareTo(currentTime) > 0);
	}

	public boolean hasPupil(Pupil p) {
		return schoolRoll.contains(p);
	}

	public boolean hasPupil(String n) {
		return schoolRoll.contains(new Person(n));
	}

	public boolean hasTeacher(Teacher t) {
		return staffList.contains(t);
	}

	public boolean hasTeacher(String n) {
		return staffList.contains(new Person(n));
	}
}
