package PEO.test;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import PEO.src.*;

public class MeetingRoomTest {

	Teacher o;
	Teacher tech;
	Pupil p;
	Pupil std;
	MeetingRoom m;
	MeetingRoom m1;
	DateAndTime st;

	@Before
	public void initialize() {
		o = new Teacher("Savi", "Engineering");
		tech = new Teacher("brad", "design");
		p = new Pupil("Jane Doe");
		std = new Pupil("John Doe");
		m = new MeetingRoom(o);
		m1 = new MeetingRoom(tech);
		st = new DateAndTime(4, 7);

	}

	// Open: Teacher opens the meeting room at the start of the parents' evening.
	// Close: Teacher closes the meeting room.
	// There may be many other operations to support viewing and changing
	// appointments.

	@Test
	public void whenConstructionThenParentNotPresent() {
		assertEquals("Parent was present at construction.", false, m.getParentPresent());
	}

	@Test
	public void whenConstructionThenClosed() {
		assertEquals("Room was open at Construction.", false, m.isOpen());
	}

	@Test
	public void whenCloseThenClosed() {
		m.open();
		m.close();
		assertEquals("Room was open after closing.", false, m.isOpen());
	}

	@Test
	public void testSetSchedule() {
		// Tests with negatives: 
		//Negative meeting length should not occur in normal use, but when used should lead to each meeting in the schedule starting earlier than the last
		
		m1.setSchedule(st, 1, -1);

		assertEquals(true, m1.getSchedule().size() == 1
				&& m1.getSchedule().get(0).getStartTime().compareTo(new DateAndTime(4, 7)) == 0);

		m1.setSchedule(st, 2, -1);

		assertEquals(true, m1.getSchedule().size() == 2
				&& m1.getSchedule().get(1).getStartTime().compareTo(new DateAndTime(4, 6)) == 0);
		// A negative number of meeting gets treated as scheduling no meetings.
		m1.setSchedule(st, -1, 1);

		assertEquals(true, m1.getSchedule().size() == 0);

		// test with zeroes:
		//When scheduling 0 meetings, the size of a schedule should be 0
		m1.setSchedule(st, 0, 1);

		assertEquals(true, m1.getSchedule().size() == 0);
		//When Scheduling meetings of length 0, they should all start at the same start time
		m1.setSchedule(st, 2, 0);

		assertEquals(true, m1.getSchedule().size() == 2
				&& m1.getSchedule().get(1).getStartTime().compareTo(new DateAndTime(4, 7)) == 0);

		// test with positives (control for normal result)
		// This is the only case that should occur during normal use
		m1.setSchedule(st, 2, 5);
		assertEquals(true, m1.getSchedule().size() == 2
				&& m1.getSchedule().get(1).getStartTime().compareTo(new DateAndTime(4, 12)) == 0);
	}
	// (With various meetingNumber and meetingLength)

	@Test
	public void givenOpenAndAppointmentNowWhenJoinThenEnter() {
		m.setSchedule(st, 1, 5);
		m.makeAppointment(p, 0);
		m.open();
		m.join(p);
		assertEquals("Parent was not able to join open meeting room for their appointment.", true,
				m.getParentPresent());
	}

	@Test
	public void givenClosedWhenJoinThenRefused() {
		m.setSchedule(st, 1, 5);
		m.makeAppointment(p, 0);
		m.open();
		m.close();
		m.join(p);
		assertEquals("Parent was able to join closed meeting room .", false, m.getParentsWaitingInLobby().contains(p));
	}

	@Test
	public void givenAppointmentFutureWhenJoinThenParentInLobby() {
		m.setSchedule(st, 2, 5);
		m.makeAppointment(p, 1);
		m.open();
		m.join(p);
		assertEquals("Parent was able to join despite appointment being in the future meeting room .", false,
				m.getParentPresent());
	}

	@Test
	public void givenNoAppointmentWhenJoinThenRejected() {
		m.setSchedule(st, 5, 8);
		m.open();
		m.join(p);

		assertEquals("Parent was able to join meeting room without an appointment.", false, m.getParentPresent());
	}

	@Test
	public void givenAppointmentPastWhenJoinThenRejected() {
		m.setSchedule(st, 5, 8);
		m.makeAppointment(p, 1); // change current time slot to one after the booked one
		m.open();
		m.join(p);
		assertEquals(
				"Parent was able to join an appointment that was scheduled for before the current slot in the meeting room.",
				false, m.getParentPresent());
	}

	@Test
	public void givenInMeetingRoomWhenLeaveThenNoParentPresent() {
		m.setSchedule(st, 2, 5);
		m.makeAppointment(p, 1);
		m.open();
		m.join(p);
		m.leave(p);
		assertEquals("Parent is still present at the appointment in the meeting room.", false, m.getParentPresent());
	}

	@Test
	public void givenInLobbyWhenLeaveThenRemoved() {
		m.setSchedule(st, 2, 5);
		m.open();
		m.join(p);
		m.leave(p);
		assertEquals("Parent is still in the lobby.", false, m.getParentsWaitingInLobby().contains(p));
	}

	@Test
	public void givenInMeetingRoomWhenAllChangeThenRemoved() {
		m.setSchedule(st, 2, 5);
		m.makeAppointment(p, 0);
		m.open();
		m.join(p);
		m.allChange();
		assertEquals("Parent is still in the meeting room.", false, m.getParentPresent());
	}

	@Test
	public void givenWaitingAndAppointmentNextWhenAllChangeThenEnter() {
		m.setSchedule(st, 2, 5);
		m.makeAppointment(p, 1);
		m.open();
		m.join(p);
		m.allChange();
		assertEquals("Parent is not in the meeting room.", true, m.getParentPresent());
	}

	@Test
	public void givenUnavailableTimeslotsWhenMakeAppointmentThenFalse() {
		m.setSchedule(st, 2, 5);
		m.makeAppointment(p, 1);
		assertEquals("Parent was able to book a time slot that is unavailable", false, m.makeAppointment(std, 1));
		System.out.println(m.getSchedule().get(1).getBookedFor());

	}

	@Test
	public void givenAvailableTimeslotWhenMakeAppointmentThenTrue() {
		m.setSchedule(st, 2, 5);
		assertEquals("Parent was not able to book a time slot that was available", true, m.makeAppointment(std, 1));
	}

}
