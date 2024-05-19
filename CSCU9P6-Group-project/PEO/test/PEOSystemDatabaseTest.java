package PEO.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import PEO.src.*;

public class PEOSystemDatabaseTest {

	PEOSystemDatabase db;

	@Before
	public void initialize() {
		db = new PEOSystemDatabase();
		db.setBookingsOpenTime(new DateAndTime(0, 50));
		db.setBookingsCloseTime(new DateAndTime(0, 100));
		db.setParentsEveningStartTime(new DateAndTime(0, 150));
		db.setParentsEveningEndTime(new DateAndTime(0, 200));
		db.setMeetingLength(5);
	}
	
	@After
	public void clear() {
		db = null;
	}

	@Test
	public void testPEOSystemDatabase() {
		assertTrue("PEOSystemDatabase did not initialize successfully.", db instanceof PEOSystemDatabase);
	}

	@Test
	public void testGetBookingsOpenTime() {
		db.setBookingsOpenTime(new DateAndTime(1, 1));
		assertTrue("getBookingsOpenTime failed to retrieve the correct value.",
				db.getBookingsOpenTime().compareTo(new DateAndTime(1, 1)) == 0);
		db.setBookingsOpenTime(new DateAndTime(2, 2));
		assertTrue("getBookingsOpenTime does not update values.",
				db.getBookingsOpenTime().compareTo(new DateAndTime(2, 2)) == 0);
	}

	@Test
	public void testSetBookingsOpenTime() {
		db.setBookingsOpenTime(new DateAndTime(0, 55));
		assertTrue("setBookingsOpenTime failed to set the correct value.",
				db.getBookingsOpenTime().compareTo(new DateAndTime(0, 55)) == 0);
		db.prepareForBookings();
		db.setBookingsOpenTime(new DateAndTime(0, 60));
		assertFalse("setBookingsOpenTime updates values in the wrong state..",
				db.getBookingsOpenTime().compareTo(new DateAndTime(0, 60)) == 0);
	}

	@Test
	public void testGetBookingsCloseTime() {
		db.setBookingsCloseTime(new DateAndTime(1, 1));
		assertTrue("getBookingsCloseTime failed to retrieve the correct value.",
				db.getBookingsCloseTime().compareTo(new DateAndTime(1, 1)) == 0);
		db.setBookingsCloseTime(new DateAndTime(2, 2));
		assertTrue("getBookingsCloseTime does not update values.",
				db.getBookingsCloseTime().compareTo(new DateAndTime(2, 2)) == 0);
	}

	@Test
	public void testSetBookingsCloseTime() {
		db.setBookingsCloseTime(new DateAndTime(0, 105));
		assertTrue("setBookingsCloseTime failed to set the correct value.",
				db.getBookingsCloseTime().compareTo(new DateAndTime(0, 105)) == 0);
		db.prepareForBookings();
		db.setBookingsCloseTime(new DateAndTime(0, 110));
		assertFalse("setBookingsCloseTime updates values in the wrong state..",
				db.getBookingsCloseTime().compareTo(new DateAndTime(0, 110)) == 0);
	}

	@Test
	public void testGetParentsEveningStartTime() {
		db.setParentsEveningStartTime(new DateAndTime(1, 1));
		assertTrue("getParentsEveningStartTime failed to retrieve the correct value.",
				db.getParentsEveningStartTime().compareTo(new DateAndTime(1, 1)) == 0);
		db.setParentsEveningStartTime(new DateAndTime(2, 2));
		assertTrue("getParentsEveningStartTime does not update values.",
				db.getParentsEveningStartTime().compareTo(new DateAndTime(2, 2)) == 0);
	}

	@Test
	public void testSetParentsEveningStartTime() {
		db.setParentsEveningStartTime(new DateAndTime(0, 155));
		assertTrue("setParentsEveningStartTime failed to set the correct value.",
				db.getParentsEveningStartTime().compareTo(new DateAndTime(0, 155)) == 0);
		db.prepareForBookings();
		db.setParentsEveningStartTime(new DateAndTime(0, 160));

		assertFalse("setParentsEveningStartTime updates values in the wrong state.",
				db.getParentsEveningStartTime().compareTo(new DateAndTime(0, 160)) == 0);
	}

	@Test
	public void testGetParentsEveningEndTime() {
		db.setParentsEveningEndTime(new DateAndTime(1, 1));
		assertTrue("getParentsEveningEndTime failed to retrieve the correct value.",
				db.getParentsEveningEndTime().compareTo(new DateAndTime(1, 1)) == 0);
		db.setParentsEveningEndTime(new DateAndTime(2, 2));
		assertTrue("getParentsEveningEndTime does not update values.",
				db.getParentsEveningEndTime().compareTo(new DateAndTime(2, 2)) == 0);
	}

	@Test
	public void testSetParentsEveningEndTime() {
		db.setParentsEveningEndTime(new DateAndTime(0, 205));
		assertTrue("setParentsEveningEndTime failed to set the correct value.",
				db.getParentsEveningEndTime().compareTo(new DateAndTime(0, 205)) == 0);
		db.prepareForBookings();
		db.setParentsEveningEndTime(new DateAndTime(0, 210));
		assertFalse("setParentsEveningEndTime updates values in the wrong state.",
				db.getParentsEveningEndTime().compareTo(new DateAndTime(0, 210)) == 0);
	}

	@Test
	public void testGetMeetingLength() {
		db.setMeetingLength(1);
		assertTrue("getMeetingLength failed to retrieve the correct value.", db.getMeetingLength() == 1);
		db.setMeetingLength(2);
		assertTrue("getMeetingLength does not update values.", db.getMeetingLength() == 2);
	}

	@Test
	public void testSetMeetingLength() {
		db.setMeetingLength(1);
		assertTrue("setMeetingLength failed to set the correct value.", db.getMeetingLength() == 1);
		db.prepareForBookings();
		db.setMeetingLength(2);
		assertFalse("setMeetingLength updates values in the wrong state.", db.getMeetingLength() == 2);
	}

	@Test
	public void testGetCurrentMeetingTimeslot() {
		db.setCurrentMeetingTimeslot(1);
		assertTrue("getCurrentMeetingTimeslot failed to retrieve the correct value.",
				db.getCurrentMeetingTimeslot() == 1);
		db.setCurrentMeetingTimeslot(2);
		assertTrue("getCurrentMeetingTimeslot does not update values.", db.getCurrentMeetingTimeslot() == 2);
	}

	@Test
	public void testSetCurrentMeetingTimeslot() {
		db.setCurrentMeetingTimeslot(1);
		assertTrue("setMeetingLength failed to set the correct value.", db.getCurrentMeetingTimeslot() == 1);
	}

	@Test
	public void testIncrementCurrentMeetingTimeslot() {
		db.setCurrentMeetingTimeslot(1);
		db.incrementCurrentMeetingTimeslot();
		assertTrue("getCurrentMeetingTimeslot failed to increase the correct value by 1.",
				db.getCurrentMeetingTimeslot() == 2);
	}
	
	@Test
	public void testGetStaffListAsString() {
		db.addTeacher("Aaron","Science");
		db.addTeacher("Bob","Cloning");
		ArrayList<String[]> output = db.getStaffListAsString();
		assertTrue("getStaffListAsString did not return all staff members.", output.size() == 2);
		assertTrue("getStaffListAsString did not return the expected number of fields.", output.get(0).length == 2);
		System.out.println(output.get(0)[0]);
		assertTrue("getStaffListAsString did not return teacher's names correctly.", output.get(0)[0] == "Aaron");
		assertTrue("getStaffListAsString did not return teacher's subjects correctly.", output.get(0)[1] == "Science");
	}
	
	@Test
	public void testGetSchoolRollAsString() {
		db.addPupil("Aaron","Mike");
		db.addPupil("Bob","June");
		ArrayList<String[]> output = db.getSchoolRollAsString();
		assertTrue("getSchoolRollAsString did not return all students.", output.size() == 2);
		assertTrue("getSchoolRollAsString did not return the expected number of fields.", output.get(0).length == 2);
		System.out.println(output.get(0)[0]);
		assertTrue("getSchoolRollAsString did not return pupil's names correctly.", output.get(0)[0] == "Aaron");
		assertTrue("getSchoolRollAsString did not return pupil's contact parents correctly.", output.get(0)[1] == "Mike");
	}

	@Test
	public void testClearNotifications() {
		db.addPupil("Andrew", "Beatrice");
		db.sendNotification("Andrew", "Bios", false);
		db.sendNotification("Andrew", "Failure", false);
		db.clearNotifications();
		assertTrue("clearNotifications failed to empty the notifications list.", db.getNotifications().size() == 0);
	}

	@Test
	public void testAddPupil() {
		assertTrue("addPupil failed to initialize the pupil object.", db.addPupil("Adam", "Bethany"));
		assertTrue("addPupil failed to add the pupil to the school roll.", db.getSchoolRoll().size() == 1);
		assertTrue("addPupil failed to store the pupil's name correctly.",
				db.getPupil("Adam").getName().equals("Adam"));
		assertTrue("addPupil failed to store the pupil's contact parent correctly.",
				db.getPupil("Adam").getContactParent().getName().equals("Bethany"));
		assertFalse("addPupil failed to check for duplicate names in pupils.", db.addPupil("Adam", "Bethany"));
		assertFalse("addPupil created an object with null values.", db.addPupil(null, null));
		assertFalse("addPupil created an object with empty values.", db.addPupil(" ", " "));
		db.prepareForBookings();
		assertFalse("addPupil allowed for pupil creation outside of the Dormant state.", db.addPupil("Charlie", "Bethany"));
	}

	@Test
	public void testEditPupil() {
		// Testing ability to check for valid pupil
		assertFalse("editPupil failed to check if pupil existed when editing.",
				db.editPupil("Adam", null, null, null, null));
		db.addPupil("Adam", "Bethany");
		// Checking null verification
		// To add, do this for contact parent names too, same reason
		db.editPupil("Adam", null, null, null, null);
		assertTrue("editPupil set name to null when provided null argument for the new name.",
				db.getPupil("Adam") != null);
		db.editPupil("Adam", " ", null, null, null);
		assertTrue("editPupil changed when provided blank string for the new name.", db.getPupil("Adam") != null);
		db.editPupil("Adam", null, " ", null, null);
		assertTrue("editPupil set contact parent's name to null when provided null argument for the new name.",
				db.getPupil("Adam").getContactParent().getName().equals("Bethany"));
		// Checking renaming pupil
		db.editPupil("Adam", "Charlie", null, null, null);
		assertTrue("editPupil did not change the pupil's name.", db.getPupil("Charlie") != null);
		assertTrue("editTeacher did not remove the pupil's old data.", db.getPupil("Adam") == null);
		// Checking changing the contact parent's name
		db.editPupil("Charlie", null, "Delia", null, null);
		assertTrue("editPupil did not change the contact parent's name.",
				db.getPupil("Charlie").getContactParent().getName().equals("Delia"));
		// Checking removing and adding teachers.
		db.addTeacher("Elizabeth", "Mechanical Engineering");
		db.editPupil("Charlie", null, null, "Elizabeth", null);
		assertTrue("editPupil did not add a new teacher to taughtBy.",
				db.getPupil("Charlie").getTaughtBy().contains(new Person("Elizabeth")));
		db.editPupil("Charlie", null, null, null, "Elizabeth");
		assertFalse("editPupil did not remove a teacher from taughtBy.",
				db.getPupil("Charlie").getTaughtBy().contains(new Person("Elizabeth")));
		assertFalse("editPupil added a non-existent teacher to taughtBy.",
				db.editPupil("Charlie", null, null, "Felix", null));
		assertFalse("editPupil removed a non-existent teacher from taughtBy.",
				db.editPupil("Charlie", null, null, null, "Felix"));
		db.prepareForBookings();
		assertFalse("editPupil allowed for pupil editing outside of the Dormant state.", db.editPupil("Charlie", "Adam", "Greg", "Elizabeth", null));
	}

	@Test
	public void testRemovePupil() {
		db.addPupil("James", "Stacy");
		db.removePupil("James");
		assertFalse("removePupil failed to remove pupil.", db.getSchoolRoll().contains(new Person("James")));
		assertFalse("removePupil succeeded at removing non-existent pupil.", db.removeTeacher("Johnny"));
		assertFalse("removePupil succeeded at removing null value named pupil.", db.removeTeacher(null));
		assertFalse("removePupil succeeded at removing empty value named pupil.", db.removeTeacher(" "));
		db.prepareForBookings();
		db.addPupil("James", "Stacy");
		assertFalse("removePupil allowed for pupil removal outside of the Dormant state.", db.removePupil("James"));
	}

	@Test
	public void testAddTeacher() {
		assertTrue("addTeacher failed to initialize the teacher object.",
				db.addTeacher("Chloe", "Business Management"));
		assertTrue("addTeacher failed to add the pupil to the staff list.", db.getStaffList().size() == 1);
		assertTrue("addTeacher failed to store the teacher's name correctly.",
				db.getTeacher("Chloe").getName().equals("Chloe"));
		assertTrue("addTeacher failed to store the teacher's subject correctly.",
				db.getTeacher("Chloe").getSubject().equals("Business Management"));
		assertFalse("addTeacher failed to check for duplicate names in teachers.",
				db.addTeacher("Chloe", "Mathematics"));
		assertFalse("addTeacher created an object with null values.", db.addTeacher(null, null));
		assertFalse("addTeacher created an object with empty values.", db.addTeacher(" ", " "));
		db.prepareForBookings();
		assertFalse("addTeacher allowed for teacher creation outside of the Dormant state.", db.addTeacher("Sarah", "Business Management"));
	}

	@Test
	public void testEditTeacher() {

		// Testing ability to check for valid pupil
		assertFalse("editTeacher failed to check if pupil existed when editing.", db.editTeacher("Adam", null, null));
		db.addTeacher("Adam", "Biology");
		// Checking null verification
		// To add, do this for contact parent names too, same reason
		db.editTeacher("Adam", null, null);
		assertTrue("editTeacher set name to null when provided null argument for the new name.",
				db.getTeacher("Adam") != null);
		db.editTeacher("Adam", " ", null);
		assertTrue("editTeacher changed when provided blank string for the new name.", db.getTeacher("Adam") != null);
		// Checking renaming teacher
		db.editTeacher("Adam", "Charlie", null);
		assertTrue("editTeacher did not change the teacher's name.", db.getTeacher("Charlie") != null);
		assertTrue("editTeacher did not remove the teacher's old data.", db.getTeacher("Adam") == null);
		// Checking changing the subject
		db.editTeacher("Charlie", null, "Chemistry");
		assertTrue("editTeacher did not change the contact parent's name.",
				db.getTeacher("Charlie").getSubject().equals("Chemistry"));
		db.prepareForBookings();
		assertFalse("editTeacher allowed for teacher editing outside of the Dormant state.", db.editTeacher("Charlie", "Sam", "Physics"));
	}

	@Test
	public void testRemoveTeacher() {
		db.addTeacher("Simon", "Physical Education");
		db.removeTeacher("Simon");
		assertFalse("removeTeacher failed to remove teacher.", db.getStaffList().contains(new Person("Simon")));
		assertFalse("removeTeacher succeeded at removing non-existent teacher.", db.removeTeacher("Johnny"));
		assertFalse("removeTeacher succeeded at removing null value named teacher.", db.removeTeacher(null));
		assertFalse("removeTeacher succeeded at removing empty value named teacher.", db.removeTeacher(" "));
		db.prepareForBookings();
		db.addTeacher("Simon", "Physical Education");
		assertFalse("removeTeacher allowed for teacher removal outside of the Dormant state.", db.removeTeacher("Simon"));
	}

	@Test
	public void testSendNotification() {
		db.addTeacher("Joel", "Astrophysics");
		assertTrue("sendNotification failed to send notification to valid person.",
				db.sendNotification("Joel", "Hi!", true));
		assertFalse("sendNotification sent notification to invalid person.", db.sendNotification("Mike", "Hi!", true));
	}

	@Test
	public void testViewMeetingLog() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.updateTime(new DateAndTime(0, 156));
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateMeetingRooms();
		db.getTeacher("Adam").getMyMeetingRoom().getTimeslot(0).setMeetingLogEntry("This is a test.");
		assertTrue("ViewMeetingLog failed to retrieve the correct log.", db.viewMeetingLog().get(0).equals("This is a test."));
		assertTrue("ViewMeetingLog failed to update the value when reading a null log.", db.viewMeetingLog().get(1).equals("Adam has no meeting scheduled at Day: 0,Time: 155"));
	}

	@Test
	public void testViewMeetingLogsInTimeslot() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.updateTime(new DateAndTime(0, 156));
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateMeetingRooms();
		db.getTeacher("Adam").getMyMeetingRoom().getTimeslot(1).setMeetingLogEntry("This is a test.");
		assertTrue("testViewMeetingLogsInTimeslot failed to retrieve the correct logs.",
				db.viewMeetingLogsInTimeslot(1).get(0).equals("This is a test."));
		assertTrue("testViewMeetingLogsInTimeslot failed to retrieve the correct logs.",
				db.viewMeetingLog().get(0).equals("Adam has no meeting scheduled at Day: 0,Time: 150"));
		assertTrue("testViewMeetingLogsInTimeslot failed to return nothing when given an invalid timeslot.",
				db.viewMeetingLogsInTimeslot(-1).size() == 0);
	}

	@Test
	public void testViewMeetingLogsByTeacher() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.updateTime(new DateAndTime(0, 156));
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateMeetingRooms();
		db.getTeacher("Adam").getMyMeetingRoom().getTimeslot(0).setMeetingLogEntry("This is a test.");
		assertTrue("viewMeetingLogsByTeacher failed to retrieve the correct number of logs.",
				db.viewMeetingLogsByTeacher("Adam").size() == 10);
		assertTrue("viewMeetingLogsByTeacher failed to retrieve the correct number of logs.",
				db.viewMeetingLogsByTeacher("James").size() == 0);
	}

	@Test
	public void testViewMeetingLogsByPupil() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.updateTime(new DateAndTime(0, 156));
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateMeetingRooms();
		db.getTeacher("Adam").getMyMeetingRoom().getTimeslot(0).setBookedFor(new Pupil("James"));
		db.getTeacher("Adam").getMyMeetingRoom().getTimeslot(0).setMeetingLogEntry("This is a test.");
		assertTrue("testViewMeetingLogsInTimeslot failed to retrieve the correct number of logs.",
				db.viewMeetingLogsByPupil("James").size() == 1);
		assertTrue("testViewMeetingLogsInTimeslot failed to retrieve the correct number of logs.",
				db.viewMeetingLogsByPupil("Adam").size() == 0);
	}

	@Test
	public void testViewNotifications() {
		db.addTeacher("Joel", "Astrophysics");
		db.addTeacher("Mike", "Astrophysics");
		db.addPupil("Joel", "Adam");
		db.addPupil("Mike", "Chloe");
		assertTrue("viewNotifications found notifications to null recipient.",
				db.viewNotifications(null, false).size() == 0);
		db.sendNotification("Joel", "Test", true);
		db.sendNotification("Mike", "Test", false);
		assertTrue("viewNotifications did not find notifications to teacher recipient.",
				db.viewNotifications("Joel", true).size() > 0);
		assertTrue("viewNotifications showed a teacher's notifications to a pupil recipient with the same name.",
				db.viewNotifications("Joel", false).size() == 0);
		assertTrue("viewNotifications did not find notifications to pupil recipient.",
				db.viewNotifications("Mike", false).size() > 0);
		assertTrue("viewNotifications showed a pupil's notifications to a teacher recipient with the same name.",
				db.viewNotifications("Mike", true).size() == 0);
	}

	@Test
	public void testBookAppointmentManually() {
		db.addPupil("James", "Stacy");
		db.addPupil("Jason", "Suzie");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.updateTime(new DateAndTime(0, 156));
		assertTrue("bookAppointmentManually could not book appointment in free space.",
				db.bookAppointmentManually("James", "Adam", 0));
		assertFalse("bookAppointmentManually booked overlapping appointment.",
				db.bookAppointmentManually("Jason", "Adam", 0));
		assertFalse("bookAppointmentManually booked appointment in non-existing.",
				db.bookAppointmentManually("Jason", "Adam", -1));
	}

	@Test
	public void testBookAppointmentsAutomaticallyFirstFree() {
		db.addPupil("James", "Stacy");
		db.addPupil("Jason", "Suzie");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.updateTime(new DateAndTime(0, 156));
		assertTrue("bookAppointmentsAutomaticallyFirstFree could not find first free when empty.",
				db.getFirstAvailableTimeslot("Adam") == 0);
		db.bookAppointmentManually("James", "Adam", 0);
		assertTrue("bookAppointmentsAutomaticallyFirstFree could not find first free when some slots are filled.",
				db.getFirstAvailableTimeslot("Adam") == 1);
	}

	@Test
	public void testBookAppointmentsAutomaticallyBetweenTwoPoints() {
		db.addPupil("James", "Stacy");
		db.addPupil("Jason", "Suzie");
		db.addPupil("Jimmy", "Sarah");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.updateTime(new DateAndTime(0, 156));
		assertTrue("bookAppointmentsAutomaticallyBetweenTwoPoints did not function as intended when empty.",
				db.getFirstAvailableTimeslotBetweenTwoPoints("Adam", 4, 5) == 4);
		db.bookAppointmentManually("James", "Adam", 4);
		assertTrue(
				"bookAppointmentsAutomaticallyBetweenTwoPoints did not function as intended when some slots are filled.",
				db.getFirstAvailableTimeslotBetweenTwoPoints("Adam", 4, 5) == 5);
		db.bookAppointmentManually("Jason", "Adam", 5);
		assertTrue(
				"bookAppointmentsAutomaticallyBetweenTwoPoints did not function as intended when all slots are filled.",
				db.getFirstAvailableTimeslotBetweenTwoPoints("Adam", 4, 5) == -1);

	}

	@Test
	public void testBookAppointmentsAutomaticallyExcludingTimeslots() {
		db.addPupil("James", "Stacy");
		db.addPupil("Jason", "Suzie");
		db.addPupil("Jimmy", "Sarah");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.updateTime(new DateAndTime(0, 156));
		ArrayList<Integer> exclude0 = new ArrayList<Integer>();
		ArrayList<Integer> exclude1 = new ArrayList<Integer>();
		exclude1.add(1);
		ArrayList<Integer> exclude2 = new ArrayList<Integer>();
		for (int x = 0; x <= 9; x++) {
			exclude2.add(x);
		}
		assertTrue("bookAppointmentsAutomaticallyExcludingTimeslots did not function as intended when empty.",
				db.getFirstAvailableTimeslotExcludingTimeslots("Adam", exclude0) == 0);
		db.bookAppointmentManually("James", "Adam", 0);
		assertTrue(
				"bookAppointmentsAutomaticallyExcludingTimeslots did not function as intended when some slots are excluded and filled.",
				db.getFirstAvailableTimeslotExcludingTimeslots("Adam", exclude1) == 2);
		assertTrue(
				"bookAppointmentsAutomaticallyExcludingTimeslots did not function as intended when all slots are excluded.",
				db.getFirstAvailableTimeslotExcludingTimeslots("Adam", exclude2) == -1);
	}

	@Test
	public void testGetAllAvailableTimeslots() {
		db.addPupil("James", "Stacy");
		db.addPupil("Jason", "Suzie");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		assertTrue("getAllAvailableTimeslots did not function as intended when empty.",
				db.getAllAvailableTimeslots("Adam").size() == 10);
		db.bookAppointmentManually("James", "Adam", 5);
		assertTrue(
				"getAllAvailableTimeslots did not function as intended when some slots are filled.",
				db.getAllAvailableTimeslots("Adam").size() == 9);
	}

	@Test
	public void testSetAvailability() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.updateTime(new DateAndTime(0, 156));
		db.prepareForBookings();
	}

	@Test
	public void testSetupMeetingRoomTimeslots() {
		// This is automatically tested by testPrepareForBookings, whose method calls
		// it.
		assertTrue(true);
	}

	@Test
	public void testPrepareForBookings() {
		assertTrue("Database did not initialize state properly.", db.getSystemStatus() == 0);
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.setMeetingLength(4);
		assertFalse("State transisiton succeeded despite indivisible meeting length.", db.prepareForBookings());
		db.setMeetingLength(5);
		assertTrue("State transition failed internally.", db.prepareForBookings());
		assertTrue("Meeting room timeslots set up incorrectly.",
				db.getTeacher("Adam").getMyMeetingRoom().getSchedule().size() == 10);
	}

	@Test
	public void testOpenBookings() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		assertTrue("State transition failed internally.", db.openBookings());
		assertTrue("Notifications were not sent to pupils.", db.viewNotifications("James", false).size() == 1);
	}

	@Test
	public void testCloseBookings() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		assertTrue("State transition failed internally.", db.closeBookings());
	}

	@Test
	public void testBeginParentsEvening() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		assertTrue("State transition failed internally.", db.beginParentsEvening());
		assertTrue("Failed to open meeting rooms.", db.getTeacher("Adam").getMyMeetingRoom().isOpen());
	}

	@Test
	public void testReturnToDormant() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		assertTrue("State transition failed internally.", db.returnToDormant());
		assertFalse("Failed to close meeting rooms.", db.getTeacher("Adam").getMyMeetingRoom().isOpen());
	}

	@Test
	public void testUpdateTime() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		assertTrue("Incorrect starting time for the database",
				db.getCurrentTime().compareTo(new DateAndTime(0, 0)) == 0);
		db.updateTime(new DateAndTime(0, 5));
		assertTrue("Time within the database did not advance",
				db.getCurrentTime().compareTo(new DateAndTime(0, 5)) == 0);
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateTime(new DateAndTime(0, 156));
		assertTrue("Current timeslot within the database was not correctly changed",
				db.getCurrentMeetingTimeslot() == 1);
	}

	@Test
	public void testUpdateMeetingRooms() {
		db.addPupil("James", "Stacy");
		db.addTeacher("Adam", "Biology");
		db.prepareForBookings();
		db.openBookings();
		db.closeBookings();
		db.beginParentsEvening();
		db.updateTime(new DateAndTime(0, 156));
		assertTrue("Update failed internally.", db.updateMeetingRooms());
		assertTrue("Current timeslot within meeting rooms was not correctly updated.",
				db.getTeacher("Adam").getMyMeetingRoom().getCurrentMeetingTimeslot() == 1);
	}

	@Test
	public void testHasPupil() {
		Pupil p = new Pupil("Adam");
		Pupil p2 = new Pupil("Arthur");
		db.addPupil("Adam", "Mad");
		assertTrue("hasPupil could not find valid pupil.", db.hasPupil(p));
		assertTrue("hasPupil could not find valid pupil.", db.hasPupil("Adam"));
		assertFalse("hasPupil found invalid pupil.", db.hasPupil(p2));
		assertFalse("hasPupil found invalid pupil.", db.hasPupil("Arthur"));
	}

	@Test
	public void testHasTeacher() {
		Teacher t = new Teacher("Adam", "Stuff");
		Teacher t2 = new Teacher("Arthur", "Stuffless");
		db.addTeacher("Adam", "Stuff");
		assertTrue("hasTeacher could not find valid teacher.", db.hasTeacher(t));
		assertTrue("hasTeacher could not find valid teacher.", db.hasTeacher("Adam"));
		assertFalse("hasTeacher found invalid teacher.", db.hasTeacher(t2));
		assertFalse("hasTeacher found invalid teacher.", db.hasTeacher("Arthur"));
	}

}
