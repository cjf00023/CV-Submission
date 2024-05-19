package PEO.src;

import java.util.Observable;
import java.util.Observer;

/**
 * This class monitors the current time and checks when significant times have been reached,
 * triggering updates in the PEO system.
 *
 * The TimeHandler can be implemented as an Observer, observing the SystemTimer. When it is notified of a change in the time, 
 * its update operation will check if a significant time has been passed and update the PEO system or the meeting rooms accordingly. 
 * It implements the behaviour shown in the System Timer use case.
 */
@SuppressWarnings("deprecation")
public class TimeHandler implements Observer{

	private SystemTimer timer;
	private PEOSystemDatabase thePEOSystem;

	public TimeHandler(SystemTimer timer, PEOSystemDatabase thePEOSystem) {
		this.timer = timer;
		this.thePEOSystem = thePEOSystem;
		thePEOSystem.updateTime(timer.getNow());
		timer.addObserver(this);
	}

	/**
	 * This operation will check whether the current time has reached or passed significant times, 
	 * and will trigger changes in the system status and call other system operations as needed.
	 * 
	 * The system goes through all the time based state changes in order, as it is possible for a time change to skip a state.
	 */
	public void triggerSystemUpdates() {
		thePEOSystem.updateTime(timer.getNow());
		//All comparisions in this method are phrased as "is current time the same or after this significant time"
		if (timer.getNow().compareTo(thePEOSystem.getBookingsOpenTime()) >= 0) {
			thePEOSystem.openBookings();
			thePEOSystem.setSystemStatusMessage("Bookings are now open.");
		}
		
		if (timer.getNow().compareTo(thePEOSystem.getBookingsCloseTime()) >= 0) {
			thePEOSystem.closeBookings();
			thePEOSystem.setSystemStatusMessage("Bookings are now closed.");
		}
		
		if (timer.getNow().compareTo(thePEOSystem.getParentsEveningStartTime()) >= 0) {
			thePEOSystem.beginParentsEvening();
			thePEOSystem.setSystemStatusMessage("Parents Evening has started.");
		}
		
		if (timer.getNow().compareTo(thePEOSystem.getParentsEveningEndTime()) >= 0) {
			thePEOSystem.returnToDormant();
			thePEOSystem.setSystemStatusMessage("Parents Evening has Ended.");
		}
	}

	/**
	 * The code checking for whether meetingRooms actually need updating at this time is stored in PEOSystemDatabase
	 */
	public void triggerMeetingRoomUpdates() {
		thePEOSystem.updateMeetingRooms();
	}

	@Override
	public void update(Observable o, Object arg) {
		triggerSystemUpdates();
		triggerMeetingRoomUpdates();
	}		
}